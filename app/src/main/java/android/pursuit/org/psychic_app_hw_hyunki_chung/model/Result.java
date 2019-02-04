package android.pursuit.org.psychic_app_hw_hyunki_chung.model;

public class Result {
    private int num_attempts;
    private int num_correct;

    public Result(int num_attempts, int num_correct) {
        this.num_attempts = num_attempts;
        this.num_correct = num_correct;
    }

    public int getNum_attempts() {
        return num_attempts;
    }

    public void setNum_attempts(int num_attempts) {
        this.num_attempts = num_attempts;
    }

    public int getNum_correct() {
        return num_correct;
    }

    public void setNum_correct(int num_correct) {
        this.num_correct = num_correct;
    }
}
