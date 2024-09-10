"use strict"

function OperationPrototype(func, operStr, diffFunc) {
    this.func = func;
    this.operStr = operStr;
    this.diffFunc = diffFunc;
    this.countArgs = func.length === 0 ? Infinity : func.length
}

OperationPrototype.prototype.evaluate = function (...vars) {
    return this.func(...this.args.map(el => el.evaluate(...vars)));
};
OperationPrototype.prototype.toString = function () {
    return this.args.join(" ").concat(" ", this.operStr);
};
OperationPrototype.prototype.diff = function (variable) {
    return this.diffFunc(...this.args)(...this.args.map(el => el.diff(variable)));
};
OperationPrototype.prototype.prefix = function () {
    // :NOTE: concat -> +
    return "(".concat(this.operStr, " ",
        this.args.map(el => el.prefix()).join(" "), ")");
};
OperationPrototype.prototype.postfix = function () {
    return "(".concat(this.args.map(el => el.postfix()).join(" ")," ", this.operStr, ")");
};

function operation(func, operStr, diffFunc) {
    let newOp = function (...args) {
        this.args = args;
    };
    newOp.prototype = new OperationPrototype(func, operStr, diffFunc);
    // :NOTE: .constructor
    return newOp;
}



let Add = operation(
    (a, b) => a + b,
    "+",
    (a, b) => (da, db) => new Add(da, db)
);

let Subtract = operation((a, b) => a - b,
    "-",
    (a, b) => (da, db) => new Subtract(da, db)
);

const Multiply = operation(
    (a, b) => a * b,
    "*",
    (a, b) => (da, db) => new Add(new Multiply(da, b), new Multiply(a, db))
);

let Divide = operation(
    (a, b) => a / b,
    "/",
    (a, b) => (da, db) => new Divide(
        new Subtract(new Multiply(da, b), new Multiply(a, db)),
        new Multiply(b, b)
    )
);

let Negate = operation(a => -a,
    "negate",
    a => da => new Negate(da)
);

let ArcTan = operation(
    Math.atan,
    "atan",
    a => da => new Divide(da, new Add(Const.ONE, new Multiply(a, a)))
);

let ArcTan2 = operation(
    Math.atan2,
    "atan2",
    (a, b) => (da, db) => new Divide(
            new Subtract(new Multiply(da, b), new Multiply(db, a)),
            new Add(new Multiply(a, a), new Multiply(b, b))
    )
);

function mean(...args){
    return args.reduce((acc, number) => acc + number) / args.length;
}

let Mean = operation(
    mean,
    "mean",
    (...args) => (...dArgs) => new Mean(...dArgs)
)
let Var = operation(
    (...args) => mean(...args.map(el => el * el)) - mean(...args) * mean(...args),
    "var",
    (...args) => (...dArgs) => new Subtract(
        new Mean(...args.map(el => new Multiply (new Const(2), new Multiply(el, dArgs[args.indexOf(el)])))),
        new Multiply(new Const(2), new Multiply(new Mean(...args), new Mean(...dArgs)))
    )
)

function Const(value) {
    this.value = value;
}
Const.prototype.evaluate = function (...vars) {
    return this.value;
}
Const.prototype.toString = function () {
    return this.value.toString();
}
Const.prototype.diff = function (variable) {
    return Const.ZERO;
}
Const.prototype.prefix = Const.prototype.toString;
Const.prototype.postfix = Const.prototype.toString;

Const.ONE = new Const(1);
Const.ZERO = new Const(0);

let VARIABLES = {"x": 0, "y": 1, "z": 2};
function Variable(variable) {
    this.variable = variable;
    this.index = VARIABLES[variable];
}

Variable.prototype.evaluate = function (...vars) {
    return vars[this.index];
}
Variable.prototype.toString = function () {
    return this.variable;
}
Variable.prototype.diff = function (variable) {
    return variable === this.variable ? Const.ONE : Const.ZERO;
}
Variable.prototype.prefix = Variable.prototype.toString;
Variable.prototype.postfix = Variable.prototype.toString;

let OPERATIONS = {
    "+" : Add,
    "-" : Subtract,
    "*" : Multiply,
    "/" : Divide,
    "atan2" : ArcTan2,
    "negate" : Negate,
    "atan" : ArcTan,
    "mean" : Mean,
    "var": Var
};

function parse(str) {
    let stack = [];
    for (let element of str.trim().split(/\s+/g)) {
        if (element in OPERATIONS){
            let Op = OPERATIONS[element];
            stack.push(new Op(...stack.splice(-Op.prototype.countArgs)));
        }else {
            stack.push(element in VARIABLES ?
                    // :NOTE: new Variable
                new Variable(element) : new Const(parseFloat(element)));
        }
    }
    return stack.pop();
}

let point = 0;

class ParseError extends Error{
    constructor(message) {
        super(message);
        this.name = "ParseError";
    }
}
function parseToken(str){
    let start = point;
    while(str.length > point && (str[point].trim() !== '' && str[point] !== ")"
            && str[point] !== "("
        )){
        point++;
    }
    return str.slice(start, point);
}
function skipWhitespace(str){
    while(str.length > point && str[point].trim() === ''){
        point++;
    }
}

function createOperation(mod, stack){
    let index = stack.length - 1;
    while (stack[index] !== '('){
        index--;
        if(index === -1){
            throw new ParseError("no opening bracket at position: " + point);
        }
    }
    let args;
    let op;
    if(mod === 1) {
        op = stack.splice(index + 1, 1);
        args = stack.splice(index + 1);
    }else {
        op = stack.splice(stack.length - 1);
        args = stack.splice(index + 1);
    }
    if( !(op in OPERATIONS)){
        throw new ParseError("unknown operation: " + op + ". At position: " + point);
    }
    for (let el of args){
        if(el in OPERATIONS){
            throw new ParseError("no closing bracket, at position: " + point);
        }
    }
    stack.pop()
    if(args.length !== OPERATIONS[op].prototype.countArgs && OPERATIONS[op].prototype.countArgs !== Infinity){
        throw new ParseError("too many arguments, at position: " + point);
    }
    stack.push(new OPERATIONS[op](...args));
}

function parsePrefix(str){
    return parseMod(str, 1);
}
function parsePostfix(str){
    return parseMod(str, 0);
}
function parseMod(str, mod) {
    let balance = 0;
    let stack = [];
    point = 0;
    while(str.length > point) {
        skipWhitespace(str);
        if (str[point] === '(') {
            balance++;
            point++;
            stack.push("(");
        }else if(str[point] === ")"){
            balance--;
            point++;
            if(balance < 0){
                throw new ParseError("unexpected closing bracket at position: " + point);
            }
            createOperation(mod, stack);
        }
        else {
            let token = parseToken(str);
            if(token.length === 0){
                continue;
            }
            if(!isNaN(token)){
                stack.push(new Const(parseFloat(token)))
            } else if(token in VARIABLES) {
                stack.push(new Variable(token));
            } else if(token in OPERATIONS){
                stack.push(token);
            } else{
                throw new ParseError("unknown symbol: " + token + ". At position: " + point);
            }
        }
    }
    if(balance > 0){
        throw new ParseError("no closing bracket");
    }
    if(stack.length !== 1){
        throw new ParseError("no operation");
    }
    return stack[0];
}


let exp = "(y negate)"
println(parsePostfix(exp))



