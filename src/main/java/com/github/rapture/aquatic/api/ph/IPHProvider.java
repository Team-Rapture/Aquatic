package com.github.rapture.aquatic.api.ph;

public interface IPHProvider {

    void setPH(int amount);
    void addPH(int amount);
    void extractPH(int amount);
}
