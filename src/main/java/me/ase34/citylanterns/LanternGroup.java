package me.ase34.citylanterns;

public class LanternGroup {

    private String name;
    private long daytime;
    private long nighttime;
    private boolean thunder;

    public LanternGroup(String name, long daytime, long nighttime, boolean thunder) {
        this.name = name;
        this.daytime = daytime;
        this.nighttime = nighttime;
        this.thunder = thunder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDaytime() {
        return daytime;
    }

    public void setDaytime(long daytime) {
        this.daytime = daytime;
    }

    public long getNighttime() {
        return nighttime;
    }

    public void setNighttime(long nighttime) {
        this.nighttime = nighttime;
    }

    public boolean isThunder() {
        return thunder;
    }

    public void setThunder(boolean thunder) {
        this.thunder = thunder;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (daytime ^ (daytime >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (int) (nighttime ^ (nighttime >>> 32));
        result = prime * result + (thunder ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LanternGroup)) {
            return false;
        }
        LanternGroup other = (LanternGroup) obj;
        if (daytime != other.daytime) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (nighttime != other.nighttime) {
            return false;
        }
        if (thunder != other.thunder) {
            return false;
        }
        return true;
    }

}