package nl.tudelft.instrumentation.patching;

import java.util.*;

public class PatchingLab {
        static final int K = 6;
        static Random r = new Random();
        static boolean isFinished = false;
        static String[] operators;
        static HashMap<Integer, double[]> tarantula = new HashMap<>();
        static HashSet<Integer> encountered_statements = new HashSet<>();
        static HashMap<Integer, Double> tarantulaScores = new HashMap<>();
        static String[] possibleOperators = {"!=", "==", "<", ">", "<=", ">="};

        static boolean just_a_fitness_test = false;

        static void initialize() {
                // initialize the population based on OperatorTracker.operators
                operators = OperatorTracker.operators;
        }

        // encounteredOperator gets called for each operator encountered while running
        // tests
        static boolean encounteredOperator(String operator, int left, int right, int operator_nr) {
                // Do something useful
                if (!just_a_fitness_test) {
                        encountered_statements.add(operator_nr);
                }

                String replacement = OperatorTracker.operators[operator_nr];
                if (replacement.equals("!="))
                        return left != right;
                if (replacement.equals("=="))
                        return left == right;
                if (replacement.equals("<"))
                        return left < right;
                if (replacement.equals(">"))
                        return left > right;
                if (replacement.equals("<="))
                        return left <= right;
                if (replacement.equals(">="))
                        return left >= right;
                return false;
        }

        static boolean encounteredOperator(String operator, boolean left, boolean right, int operator_nr) {
                // Do something useful
                if (!just_a_fitness_test) {
                        encountered_statements.add(operator_nr);
                }

                String replacement = OperatorTracker.operators[operator_nr];
                if (replacement.equals("!="))
                        return left != right;
                if (replacement.equals("=="))
                        return left == right;
                return false;
        }

        static void updateTarantula(Boolean test_failed) {
                for (int statement : encountered_statements) {
                        double[] t_statement;
                        if (tarantula.containsKey(statement)) {
                                t_statement = tarantula.get(statement);
                        } else {
                                t_statement = new double[] { 0.0, 0.0 };
                        }

                        if (test_failed) {
                                t_statement[0]++;
                        } else {
                                t_statement[1]++;
                        }

                        tarantula.put(statement, t_statement);
                }
        }

        static void tarantulaScore(double failedTests) {
                double score = 0.0;
                double passedTests = OperatorTracker.tests.size() - failedTests;

                System.out.println("Failed tests: " + failedTests);
                System.out.println("Passed tests: " + passedTests);

                for (Map.Entry<Integer, double[]> s : tarantula.entrySet()) {
                        score = (s.getValue()[0] / (failedTests + 1))
                                        / ((s.getValue()[0] / (failedTests + 1)) + (s.getValue()[1] / (passedTests + 1)));
                        tarantulaScores.put(s.getKey(), score);
                }
        }

        public static int testFitness() {
                just_a_fitness_test = true;
                int fitnessTest = 0;

                for (int i = 0; i < OperatorTracker.tests.size(); i++) {
                        try {
                                Boolean test_failed = !OperatorTracker.runTest(i);
                                if (test_failed) {
                                        fitnessTest += 1.0;
                                }

                                Thread.sleep(0);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
                just_a_fitness_test = false;

                return fitnessTest;
        }

        public static List<Integer> getTopKScores(HashMap<Integer, Double> hm){
                List<Map.Entry<Integer, Double> > list = new LinkedList<Map.Entry<Integer, Double> >(hm.entrySet());
                List<Integer> topKSuspects = new ArrayList<>();
        
                Collections.sort(list, new Comparator<Map.Entry<Integer, Double> >() {
                        public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                                return (o1.getValue()).compareTo(o2.getValue());
                        }
                });

                for(int i = list.size()-1; i >= list.size()-K; i--) {
                        topKSuspects.add(list.get(i).getKey());
                }

                return topKSuspects;
        }

        static String tournamentSelection(int top_suspect) {
                String original_statement = OperatorTracker.operators[top_suspect];
                System.out.println("\nStart of tournament selection for replacing operator " + top_suspect + " " + original_statement);
                String best_mutation = null;
                int best_mutation_score = Integer.MAX_VALUE;

                // Tournament Selection
                for (int i = 0; i < K; i++) {
                        String newMutation = possibleOperators[r.nextInt(possibleOperators.length)];
                        while (newMutation == OperatorTracker.operators[top_suspect]) {
                                newMutation = possibleOperators[r.nextInt(possibleOperators.length)];
                        }
                        OperatorTracker.operators[top_suspect] = newMutation;

                        int fitness_new_mutation;
                        if (best_mutation == null) {
                                best_mutation = newMutation;
                        }

                        System.out.print("Fitness test for " + newMutation);
                        fitness_new_mutation = testFitness();
                        System.out.println(" with fitness score " + fitness_new_mutation);
                        if (fitness_new_mutation < best_mutation_score) {
                                best_mutation = newMutation;
                                best_mutation_score = fitness_new_mutation;
                        }
                }

                return best_mutation;
        }

        static void faultLocalization() {
                List<Integer> topKSuspects = new ArrayList<>();
                topKSuspects = getTopKScores(tarantulaScores);

                Integer top_suspect = topKSuspects.get(r.nextInt(K));
                String best_mutation = tournamentSelection(top_suspect);

                OperatorTracker.operators[top_suspect] = best_mutation;

        }

        static void run() {
                double failedTests = 0.0;
                initialize();

                // Place the code here you want to run once:
                // You want to change this of cour

//                Op eratorTracker.readTests();se, this is just an example
                // Tests are loaded from resources/tests.txt, make sure you put in the right
                // tests for the right problem!
                // OperatorTracker.runAllTests();
                OperatorTracker.readTests();
                System.out.println("Algorithm until you think it is done");
                while (!isFinished) {
                        // Do things!
                        System.out.println("Tests size: " + OperatorTracker.tests.size());
                        for (int i = 0; i < OperatorTracker.tests.size(); i++) {
                                try {
                                        Boolean test_failed = !OperatorTracker.runTest(i);
                                        if (test_failed) {
                                                failedTests += 1.0;
                                        }
                                        updateTarantula(test_failed);
                                        encountered_statements.clear();

                                        Thread.sleep(0);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                        tarantulaScore(failedTests);
                        faultLocalization();

                        if(failedTests == 0.0) {
                                System.out.print("Patching is done! No more!");
                                isFinished = true;
                        } else {
                                tarantulaScores.clear();
                                tarantula.clear();
                                failedTests = 0.0;
                        }
                }
        }

        public static void output(String out) {
                // This will get called when the problem code tries to print things,
                // the prints in the original code have been removed for your convenience

                // System.out.println(out);
        }
}