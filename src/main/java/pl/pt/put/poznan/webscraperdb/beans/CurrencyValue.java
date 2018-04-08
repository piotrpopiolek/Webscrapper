package pl.pt.put.poznan.webscraperdb.beans;

import pl.pt.put.poznan.webscraperdb.CurrencyManagement;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "\"Currency Values\"", schema = "dbo", catalog = "Currencies")
public class CurrencyValue {
    private BigInteger valueId;
    private Currency currency;
    private double priceInDollars;
    private double priceInBitcoin;
    private double ath;
    private double bid;
    private double ask;
    private double volume;
    private double marketCapCurrency;
    private double marketCapExchange;
    private Date date;

    public CurrencyValue() {
    }

    public CurrencyValue(String symbol) {
        Currency myCurrency = CurrencyManagement.getInstance().getEntityByPrimaryKey(Currency.class, symbol);
        setCurrency(myCurrency);
    }

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public BigInteger getValueId() {
        return valueId;
    }

    public void setValueId(BigInteger id) {
        this.valueId = id;
    }

    @ManyToOne
    @JoinColumn(name = "Symbol", nullable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Column
    public double getPriceInDollars() {
        return priceInDollars;
    }

    public void setPriceInDollars(double priceInDollars) {
        this.priceInDollars = priceInDollars;
    }

    @Column
    public double getPriceInBitcoin() {
        return priceInBitcoin;
    }

    public void setPriceInBitcoin(double priceInBtc) {
        this.priceInBitcoin = priceInBtc;
    }

    @Column
    public double getAth() {
        return ath;
    }

    public void setAth(double ath) {
        this.ath = ath;
    }

    @Column
    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    @Column
    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    @Column
    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Column
    public double getMarketCapCurrency() {
        return marketCapCurrency;
    }

    public void setMarketCapCurrency(double marketCapCurrency) {
        this.marketCapCurrency = marketCapCurrency;
    }

    @Column
    public double getMarketCapExchange() {
        return marketCapExchange;
    }

    public void setMarketCapExchange(double marketCapExchange) {
        this.marketCapExchange = marketCapExchange;
    }

    @Column(insertable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}