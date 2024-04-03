package search;

public class BinarySearchClosestI {

    public static void main(String[] args) {
        // P: args.length != 0 && for i..n : args[i] <= args[i+1] && args[i].isInteger
        int x = Integer.parseInt(args[0]);
        // Q: x
        // P: args.length != 0
        int[] numbers = new int[args.length - 1];
        // Q: numbers
        // P: x
        for (int i = 0; i < args.length - 1; i++) {
            numbers[i] = Integer.parseInt(args[i + 1]);
        }
        // Q: numbers = args[1:] && x' = x
        int index;
        // P: args.length != 0
        index = iterativeSearch(x, 0, numbers.length - 1, numbers);
        // Q: index: minDist = numbers[index] || numbers[index - 1]
        int request;
        // P: index: minDist = numbers[index] || numbers[index - 1]
        // && numbers.length > 0
        if (index == numbers.length) {
            // P: index: minDist = numbers[index] || numbers[index - 1]
            // && numbers.length > 0 && index == numbers.length
            request = numbers[numbers.length - 1];
        } else if (index == 0) {
            // P: index: minDist = numbers[index] || numbers[index - 1]
            // && numbers.length > 0 && index == 0
            request = x;
        } else {
            // P: index: minDist = numbers[index] || numbers[index - 1]
            // && numbers.length > 0 && !index == 0 && !index == numbers.length
            int dist2 = numbers[index] - x;
            int dist1 = x - numbers[index - 1];
            // Q: minDist = min(dist1, dist2)

            if (dist1 <= dist2) {
                // P: dist1 <= dist2
                request = numbers[index - 1];
                // Q: minIndex = index - 1
            } else {
                // P: !(dist1 <= dist2)
                request = numbers[index];
                // Q: minIndex = index - 1
            }
        }
        // Q: minDist = request && minIndex == numbers.IndexOf(request)
        // P: numbers.length > 0 && minDist ∈ numbers && minIndex == numbers.IndexOf(request)
        System.out.println(recursiveSearch(request, 0, numbers.length - 1, numbers));
        // Q: R = index: numbers[index] = minDist && minIndex == numbers.IndexOf(request)

    }

    // :NOTE: пред и пост условия
    public static int iterativeSearch(int x, int left, int right, int[] numbers) {
        // P: right >= left && numbers.length > 0
        if (numbers.length <= 1) {
            // P: right >= left && numbers.length > 0 && numbers.length <= 1 ==>>
            // numbers.length == 1 ==>> minIndex = 0
            return 0;
        } else if (x <= numbers[0]) {
            // P: right >= left && numbers.length > 0 && x <= numbers[0] &&
            // for i..n : numbers[i] <= numbers[i+1] ==>> minIndex = 0
            return 0;
        } else if (x > numbers[numbers.length - 1]) {
            // P: right >= left && numbers.length > 0 && x > numbers[numbers.length - 1] &&
            // for i..n : numbers[i] <= numbers[i+1] ==>> minIndex = numbers.length -1
            return numbers.length;
        }

        // P: x ∈ (numbers[left], numbers[right]] && numbers.length > 1
        while (right - left > 1) {
            // I:(right - left > 0 && x ∈ (numbers[left], numbers[right]]
            // P: I && right - left > 1
            int mid = (left + right) / 2;
            // Q: I && right - left > 1 && left < mid < right
            if (numbers[mid] < x) {
                // P: I && right - left > 1 &&  left < mid < right &&
                // numbers[mid] < x
                left = mid;
                // Q: I && (right' - left') == (right - left) // 2
            } else {
                // P: I && right - left > 1 &&  left < mid < right &&
                // numbers[mid] >= x
                right = mid;
                // Q: I && (right' - left') == (right - left) // 2
            }
            // Q: I && (right' - left') == (right - left) // 2  ==>>
            // цикл сделает не более чем log(right - left) итераций
        }
        // Q: I && right - left <= 1 ==>>
        // right - left == 1 && x ∈ (numbers[left], numbers[right]] ==>>
        // x ∈ x ∈ (numbers[right - 1], numbers[right]]
        return right;
    }

// :NOTE: пред и пост услвоие
    public static int recursiveSearch(int x, int left, int right, int[] numbers) {
        // P: right >= left && numbers.length > 0
        if (numbers.length <= 1) {
            // P: right >= left && numbers.length > 0 && numbers.length <= 1 ==>>
            // numbers.length == 1 ==>> minIndex = 0
            return 0;
        } else if (x <= numbers[0]) {
            // P: right >= left && numbers.length > 0 && x <= numbers[0] &&
            // for i..n : numbers[i] <= numbers[i+1] ==>> minIndex = 0
            return 0;
        } else if (x > numbers[numbers.length - 1]) {
            // P: right >= left && numbers.length > 0 && x > numbers[numbers.length - 1] &&
            // for i..n : numbers[i] <= numbers[i+1] ==>> minIndex = numbers.length -1
            return numbers.length;
        }

        // P: x ∈ (numbers[left], numbers[right]] && numbers.length > 1
        if (right - left <= 1) {
            // P: x ∈ (numbers[left], numbers[right]] && right - left == 1 ==>> x ∈ (numbers[right - 1], numbers[right]]
            return right;
        }
        // Q: right - left > 1 && x ∈ (numbers[left], numbers[right]]
        int mid = (left + right) / 2;
        // Q: right - left > 1 && x ∈ (numbers[left], numbers[right]] && left < mid < right
        if (numbers[mid] < x) {
            // P: right - left > 1 && x ∈ (numbers[left], numbers[right]] && left < mid < right && numbers[mid] <= x
            left = mid;
            // Q: right - left > 0 && x ∈ (numbers[left], numbers[right]] && (right' - left') == (right - left) // 2
        } else {
            // P: right - left > 1 && x ∈ (numbers[left], numbers[right]] && left < mid < right && numbers[mid] > x
            right = mid;
            // Q: right - left > 0 && x ∈ (numbers[left], numbers[right]] && (right' - left') == (right - left) // 2
        }
        // Q: right - left > 0 && x ∈ (numbers[left], numbers[right]] && (right' - left') == (right - left) // 2

        // P: x ∈ (numbers[left], numbers[right]] && (right' - left') == (right - left) // 2
        return recursiveSearch(x, left, right, numbers);
        // Q:  x ∈ (numbers[left], numbers[right]] && (right - left == 1)
    }
}
