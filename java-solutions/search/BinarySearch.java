package search;

public class BinarySearch {
    // :NOTE: пред и пост условия на Main
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] numbers = new int[args.length - 1];
        try {
            for (int i = 0; i < args.length - 1; i++) {
                numbers[i] = Integer.parseInt(args[i + 1]);
            }
            int ans;
            if (numbers.length == 0) {
                ans = 0;
            } else if (x >= numbers[0]) {
                ans = 0;
            } else if (x < numbers[numbers.length - 1]) {
                ans = numbers.length;
            } else {
                // P: !numbers.isEmpty && x ∈ [numbers[right], numbers[left])

                ans = iterativeSearch(x, 0, numbers.length - 1, numbers);
//                ans = recursiveSearch(x, 0, numbers.length -1, numbers);
                // Q: index: x ∈ [numbers[index], numbers[index - 1]) && (right - left == 1)
            }

            System.out.println(ans);
        } catch (NumberFormatException e) {
            System.out.println("Expected integer. " + e.getMessage());
        }
    }

    public static int iterativeSearch(int x, int left, int right, int[] numbers) {
        // P: x ∈ [numbers[right], numbers[left])
        while (right - left > 1) {
            // I: (right - left > 0 && x ∈ [numbers[right], numbers[left]))
            // P: I && right - left > 1
            int mid = (left + right) / 2;
            // Q: I && right - left > 1 && left < mid < right
            if (numbers[mid] <= x) {
                // P: I && right - left > 1 && left < mid < right &&
                // numbers[mid] <= x
                right = mid;
                // Q: I && (right' - left') == (right - left) // 2
            } else {
                // P: I && right - left > 1 && left < mid < right &&
                // numbers[mid] > x
                left = mid;
                // Q: I && (right' - left') == (right - left) // 2
            }
            // Q: I && (right - left) == (right - left) // 2
        }
        // Q: I && right - left <= 1 ==>>
        // right - left == 1 && x ∈ [numbers[right], numbers[left]) ==>>
        // x ∈ [numbers[right], numbers[right - 1])
        return right;
    }


    public static int recursiveSearch(int x, int left, int right, int[] numbers) {
        // P: x ∈ [numbers[right], numbers[left])
        if (right - left <= 1) {
            // P: x ∈ [numbers[right], numbers[left]) && right - left == 1 ==>> x ∈ [numbers[right], numbers[right - 1])
            return right;
        }
        // Q/P: right - left > 1 && x ∈ [numbers[right], numbers[left])
        int mid = (left + right) / 2;
        // Q: right - left > 1 && x ∈ [numbers[right], numbers[left]) && left < mid < right
        if (numbers[mid] <= x) {
            // P: right - left > 1 && x ∈ [numbers[right], numbers[left]) && left < mid < right && numbers[mid] <= x
            right = mid;
            // Q: right - left > 0 && x ∈ [numbers[right], numbers[left]) && (right' - left') == (right - left) // 2
        } else {
            // P: right - left > 1 && x ∈ [numbers[right], numbers[left]) && left < mid < right && numbers[mid] > x
            left = mid;
            // Q: right - left > 0 && x ∈ [numbers[right], numbers[left]) && (right' - left') == (right - left) // 2
        }
        // Q: right - left > 0 && x ∈ [numbers[right], numbers[left]) && (right' - left') == (right - left) // 2

        // P: x ∈ [numbers[right], numbers[left])
        return recursiveSearch(x, left, right, numbers);
        // Q: index: x ∈ [numbers[index], numbers[index - 1]) && (right - left == 1)
    }
}
