import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BKTree {
    private static final int LIMIT = 2;
    private Node root;
    private List<Node> nodes = new ArrayList<>();

    private static class Node {
        private final String word;
        private final Map<Integer, Integer> nodeIndex;

        private Node(String word) {
            this.word = word;
            nodeIndex = new HashMap<>();
        }
    }

    private BKTree() {
    }

    static BKTree build(ArrayList<String> dictionary) {
        BKTree tree = new BKTree();
        tree.root = new Node(dictionary.get(0));
        for (int i = 1; i < dictionary.size(); ++i) {
            makeBKTree(tree, tree.root, dictionary.get(i));
        }
        return tree;
    }

    private static void makeBKTree(BKTree tree, Node root, String word) {
        int distance = getEditDistance(word, root.word);
        Integer ptr = root.nodeIndex.get(distance);

        if (null != ptr) {
            makeBKTree(tree, tree.nodes.get(ptr), word);
        } else {
            tree.nodes.add(new Node(word));
            root.nodeIndex.put(distance, tree.nodes.size() - 1);
        }
    }

    public ArrayList<String> getSimilarWords(String search) {
        ArrayList<String> result = new ArrayList<>();
        getSimilarWords(root, result, search);
        return result;
    }

    private void getSimilarWords(Node root, ArrayList<String> result, String search) {
        int distance = getEditDistance(search, root.word);

        if (distance <= LIMIT) {
            result.add(root.word);
        }

        int start = distance - LIMIT;
        if (start < 0) {
            start = 1;
        }

        while (start < distance + LIMIT) {
            Integer ptr = root.nodeIndex.get(start);

            if (null != ptr) {
                getSimilarWords(nodes.get(ptr), result, search);
            }
            start++;
        }
    }

    // Levensteina distance
    private static int getEditDistance(String a, String b) {
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

}
