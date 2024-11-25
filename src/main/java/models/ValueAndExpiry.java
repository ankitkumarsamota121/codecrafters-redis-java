package models;

public class ValueAndExpiry {
    private String value;
    private Long ttl;

    public ValueAndExpiry(final String value, final Long ttl) {
        this.value = value;
        this.ttl = ttl;
    }

    public ValueAndExpiry(final String value) {
        this.value = value;
        this.ttl = Long.MAX_VALUE;
    }

    public void setTtl(final Long ttl) {
        this.ttl = ttl;
    }

    public String getValue() {
        return value;
    }

    public Long getTtl() {
        return ttl;
    }


}
