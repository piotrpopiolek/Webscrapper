package pl.pt.put.poznan.webscraperdb.beans;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Currencies", schema = "currencies")
public class Currency {
    private String symbol;
    private String name;
    private byte[] logo;
    private long marketCap;
    private Set<CurrencyValue> currencyValues;

    @Column
    @Id
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    @Column
    public long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(long marketCap) {
        this.marketCap = marketCap;
    }

    @OneToMany(mappedBy = "currency")
    public Set<CurrencyValue> getCurrencyValues() {
        return currencyValues;
    }

    public void setCurrencyValues(Set<CurrencyValue> currencyValues) {
        this.currencyValues = currencyValues;
    }
}
