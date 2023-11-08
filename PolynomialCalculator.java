package problems;

import java.util.Scanner;

class Node {
    int power;
    int coefficient;
    Node next;

    public Node(int power, int coefficient) {
        this.power = power;
        this.coefficient = coefficient;
    }
}

public class PolynomialCalculator {
    public static Node polynomialAdd(Node head1, Node head2) {
        Node result = null;
        Node tail = null;
        Node p1 = head1;
        Node p2 = head2;

        while (p1 != null && p2 != null) {
            if (p1.power > p2.power) {
                tail = append(tail, p1.power, p1.coefficient);
                p1 = p1.next;
            } else if (p2.power > p1.power) {
                tail = append(tail, p2.power, p2.coefficient);
                p2 = p2.next;
            } else {
                int coefficient = p1.coefficient + p2.coefficient;
                if (coefficient != 0) {
                    tail = append(tail, p1.power, coefficient);
                }
                p1 = p1.next;
                p2 = p2.next;
            }
        }

        while (p1 != null) {
            tail = append(tail, p1.power, p1.coefficient);
            p1 = p1.next;
        }

        while (p2 != null) {
            tail = append(tail, p2.power, p2.coefficient);
            p2 = p2.next;
        }

        if (result == null) {
            result = tail;
        }

        return result;
    }

    public static Node polynomialSubtract(Node head1, Node head2) {
        Node result = null;
        Node tail = null;
        Node p1 = head1;
        Node p2 = head2;

        while (p1 != null && p2 != null) {
            if (p1.power > p2.power) {
                tail = append(tail, p1.power, p1.coefficient);
                p1 = p1.next;
            } else if (p2.power > p1.power) {
                tail = append(tail, p2.power, -p2.coefficient); // Subtracting second polynomial
                p2 = p2.next;
            } else {
                int coefficient = p1.coefficient - p2.coefficient; // Subtracting coefficients
                if (coefficient != 0) {
                    tail = append(tail, p1.power, coefficient);
                }
                p1 = p1.next;
                p2 = p2.next;
            }
        }

        while (p1 != null) {
            tail = append(tail, p1.power, p1.coefficient);
            p1 = p1.next;
        }

        while (p2 != null) {
            tail = append(tail, p2.power, -p2.coefficient); // Subtracting second polynomial
            p2 = p2.next;
        }

        if (result == null) {
            result = tail;
        }

        return result;
    }

    public static Node polynomialMultiply(Node head1, Node head2) {
        Node result = null;
        Node p1 = head1;

        while (p1 != null) {
            Node p2 = head2;
            while (p2 != null) {
                int power = p1.power + p2.power;
                int coefficient = p1.coefficient * p2.coefficient;
                result = addTerm(result, power, coefficient);
                p2 = p2.next;
            }
            p1 = p1.next;
        }

        return result;
    }

    public static Node append(Node tail, int power, int coefficient) {
        Node newNode = new Node(power, coefficient);
        if (tail == null) {
            return newNode;
        }

        Node current = tail;
        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
        return tail;
    }

    public static void printPolynomial(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.coefficient + "x^" + current.power);
            current = current.next;
            if (current != null && current.coefficient>0) {
                System.out.print(" + ");
            }
            else if(current != null && current.coefficient<0) {
                System.out.print(" ");

            }

        }
        System.out.println();
    }
    public static Node addTerm(Node head, int power, int coefficient) {
        Node newNode = new Node(power, coefficient);

        // If the polynomial is empty, set the new term as the head
        if (head == null) {
            return newNode;
        }

        // Find the correct position to insert the new term based on power
        Node current = head;
        Node previous = null;
        while (current != null && power < current.power) {
            previous = current;
            current = current.next;
        }

        // If a term with the same power exists, add the coefficients
        if (current != null && power == current.power) {
            current.coefficient += coefficient;
        } else {
            // Insert the new term at the appropriate position
            newNode.next = current;
            if (previous == null) {
                // If it's the new head, update head
                head = newNode;
            } else {
                previous.next = newNode;
            }
        }

        return head;
    }

    public static Node createPolynomial() {
        Scanner scanner = new Scanner(System.in);
        Node head = null;
        Node tail = null;

        System.out.print("Enter the number of terms in the polynomial: ");
        int numTerms = scanner.nextInt();

        for (int i = 0; i < numTerms; i++) {
            System.out.print("Enter the power for term " + (i + 1) + ": ");
            int power = scanner.nextInt();
            System.out.print("Enter the coefficient for term " + (i + 1) + ": ");
            int coefficient = scanner.nextInt();
            tail = append(tail, power, coefficient);
            if (head == null) {
                head = tail;
            }
        }

        return head;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Polynomial Calculator");
        System.out.println("1. Add Polynomials");
        System.out.println("2. Subtract Polynomials");
        System.out.println("3. Multiply Polynomials");
        System.out.print("Enter your choice (1, 2, or 3): ");
        int choice = scanner.nextInt();

        Node head1, head2, result;
        System.out.println("Enter the first polynomial:");
        head1 = createPolynomial();
        System.out.println("Enter the second polynomial:");
        head2 = createPolynomial();

        if (choice == 1) {
            result = polynomialAdd(head1, head2);
            System.out.println("Addition Result:");
        } else if (choice == 2) {
            result = polynomialSubtract(head1, head2);
            System.out.println("Subtraction Result:");
        } else if (choice == 3) {
            result = polynomialMultiply(head1, head2);
            System.out.println("Multiplication Result:");
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        printPolynomial(result);
    }
}