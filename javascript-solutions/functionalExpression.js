"use strict"

let variables = {"x": 0, "y": 1, "z": 2};
let operation = func => (...args) => (...vars) => func(...args.map(arg => arg(...vars)));

let add = operation((a, b) => a + b);
let subtract = operation((a, b) => a - b);
let multiply = operation((a, b) => a * b);
let divide = operation((a, b) => a / b);
let negate = operation(a => -a);
let min5 = operation((a1, a2, a3, a4, a5) => Math.min(a1, a2, a3, a4, a5));
let max3 = operation((a1, a2, a3) => Math.max(a1, a2, a3));
let test = operation((a, b, c) => (a => b)(b) + (a => c)(a) * (c => a)(c));
let variable = name => (...vars) => vars[variables[name]];
let cnst = value => (...vars) => value;
let pi = cnst(Math.PI);
let e = cnst(Math.E);


let operations = {
    "x": variable('x'),
    "y": variable('y'),
    "z": variable('z'),
    "pi": pi,
    "e": e,
    "negate": negate,
    "+": add,
    "-": subtract,
    "*": multiply,
    "/": divide,
    "max3": max3,
    "min5": min5,
    "test": test
};

let countArgs = {
    "x": 0,
    "y": 0,
    "z": 0,
    "pi": 0,
    "e": 0,
    "negate": 1,
    "+": 2, "-": 2,
    "*": 2, "/": 2,
    "test": 3,
    "max3": 3,
    "min5": 5
};
const parse = function (str) {
    let expression = str.split(" ").filter(a => a.length > 0);
    let stack = [];
    for (let element of expression) {
        if (element in operations) {
            if(-countArgs[element] !== 0) {
                let args = stack.splice(- countArgs[element]);
                stack.push(operations[element](...args));
            }else{
                stack.push(operations[element])
            }
        } else {
            stack.push(cnst(parseInt(element)));
        }
    }
    return stack[0]
}

// let megaTest = test(
//     cnst(1),
//     cnst(2),
//     cnst(4),
// )
// println(megaTest(0, 0, 0))
//
// let testExpr = multiply(
//     subtract(
//         variable('x'),
//         cnst(1)
//     ),
//     subtract(
//         variable('x'),
//         cnst(1)
//     )
// );
// for(let i = 0; i <= 10; i++){
//     println(testExpr(i, 0, 0));
// }
// println(parse("x x 2 - * x * 1 +")(5, 0, 0))

