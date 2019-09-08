package de.seine_eloquenz.weisswurstwann;

public class WWWeek {

    private final int length = 5;
    private WWStatus monday;
    private WWStatus tuesday;
    private WWStatus wednesday;
    private WWStatus thursday;
    private WWStatus friday;

    public static WWWeek error() {
        return new WWWeek(WWStatus.ERROR, WWStatus.ERROR, WWStatus.ERROR, WWStatus.ERROR, WWStatus.ERROR);
    }

    public WWWeek() {
        this.monday = WWStatus.NO_INFO;
        this.tuesday = WWStatus.NO_INFO;
        this.wednesday = WWStatus.NO_INFO;
        this.thursday = WWStatus.NO_INFO;
        this.friday = WWStatus.NO_INFO;
    }

    public WWWeek(final WWStatus monday, final WWStatus tuesday, final WWStatus wednesday, final WWStatus thursday, final WWStatus friday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }

    public void update(int dayOfWeek, WWStatus status) {
        switch (dayOfWeek) {
            case 0: monday = status;
            break;
            case 1: tuesday = status;
            break;
            case 2: wednesday = status;
            break;
            case 3: thursday = status;
            break;
            case 4: friday = status;
            break;
            default:
        }
    }

    public WWStatus monday() {
        return monday;
    }

    public WWStatus tuesday() {
        return tuesday;
    }

    public WWStatus wednesday() {
        return wednesday;
    }

    public WWStatus thursday() {
        return thursday;
    }

    public WWStatus friday() {
        return friday;
    }

    public int length() {
        return length;
    }

    public boolean errorOccured() {
        return monday.equals(WWStatus.ERROR) || tuesday.equals(WWStatus.ERROR) || wednesday.equals(WWStatus.ERROR)
                || thursday.equals(WWStatus.ERROR) || friday.equals(WWStatus.ERROR);
    }
}
