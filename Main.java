import java.util.ArrayList;

public class Main {
    float mutationRate;
    int totalPopulation = 1000;

    DNA[] population;
    DNA bestPhrase;
    ArrayList<DNA> matingPool;
    String target;

    int generations = 0;
    double avgFitness = 0;

    void print() {
        for (int i = 0; i < population.length; i++) {
            System.out.println(population[i].getPhrase() + "\tFitness: " + population[i].getFitness());
        }

        System.out.println("\nBest Phrase: " + bestPhrase.getPhrase());
        System.out.println("\ntotal generations: " + generations);
        System.out.println("average fitness: " + avgFitness);
        System.out.println("total population: " + totalPopulation);
        System.out.println("mutation rate: " + (mutationRate * 100) + "%");
    }

    void fitness_sort() {
        double max = population[0].getFitness();

        for (int i = 0; i < population.length; i++) {
            avgFitness += population[i].fitness;

            if (population[i].fitness > max) {
                max = population[i].getFitness();
                bestPhrase = population[i];
            }
        }

        avgFitness /= population.length;
        avgFitness = (double) Math.round(avgFitness * 100.0) / 100.0;
    }

    void setup() {
        target = "hi there!";
        mutationRate = 0.01f;

        population = new DNA[totalPopulation];
        for (int i = 0; i < population.length; i++) {
            population[i] = new DNA(target);
        }
   }

    void draw() {
        for (int i = 0; i < population.length; i++) {
            population[i].fitness();
        }

        ArrayList<DNA> matingPool = new ArrayList<DNA>();

        for (int i = 0; i < population.length; i++) {
            int n = (int) (population[i].getFitness() * 100);

            for (int j = 0; j < n; j++) {
                matingPool.add(population[i]);
            }
        }

        for (int i = 0; i < population.length; i++) {
            int a = (int) (Math.random() * matingPool.size());
            int b = (int) (Math.random() * matingPool.size());

            DNA partnerA = matingPool.get(a);
            DNA partnerB = matingPool.get(b);
            
            DNA child = partnerA.crossover(partnerB);

            child.mutate(mutationRate);
            child.fitness();

            population[i] = child;

            if (population[i].getFitness() == 1) break;
        }
    }

    boolean hasPerfect() {
        for (int i = 0; i < population.length; i++) {
            if (population[i].getFitness() == 1.0) return true;
        }

        return false;
    }

    public static void main(String[] args) {
        Main m = new Main();

        m.setup();

        while (!m.hasPerfect()) {
            m.draw();
            m.generations++;
        }

        m.fitness_sort();

        m.print();
    }
}