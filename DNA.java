public class DNA {
    char[] genes;
    float fitness;
    String target;

    public DNA(String t) {
        this.target = t;

        genes = new char[target.length()];

        for (int i = 0; i < genes.length; i++) {
            genes[i] = (char) random(32, 128);
        }
    }

    public void fitness() {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                score++;
            }
        }

        fitness = (float) (score) / target.length();
    }

    DNA crossover(DNA partner) {
        DNA child = new DNA(target);

        int midpoint = (int) (random(genes.length));

        for (int i = 0; i < genes.length; i++) {
            if (i > midpoint) child.genes[i] = genes[i];
            else child.genes[i] = partner.genes[i];
        }

        return child;
    }

    void mutate(float mutationRate) {
        for (int i = 0; i < genes.length; i++) {
            if (random(1) < mutationRate) {
                genes[i] = (char) (random(32, 128));
            }
        }
    }

    public String getPhrase() {
        return new String(genes);
    }

    public double getFitness() {
        return fitness;
    }

    public double random(int min, int max) {
        return (Math.random() * (max - min) + min);
    }

    public double random(int max) {
        return (Math.random() * max);
    }
}