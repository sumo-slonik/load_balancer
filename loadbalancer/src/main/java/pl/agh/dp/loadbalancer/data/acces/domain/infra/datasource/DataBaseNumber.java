package pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource;

public enum DataBaseNumber {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH,
    SEVENTH,
    EIGHTH,
    NINTH,
    TENTH;

    public DataBaseNumber next()
    {
        switch (this) {
            case FIRST:
                return SECOND;
            case SECOND:
                return THIRD;
            case THIRD:
                return FOURTH;
            case FOURTH:
                return FIFTH;
            case FIFTH:
                return SIXTH;
            case SIXTH:
                return SEVENTH;
            case SEVENTH:
                return EIGHTH;
            case EIGHTH:
                return NINTH;
            case NINTH:
                return TENTH;
            case TENTH:
                return null;
            default:
                throw new IllegalArgumentException();
        }
    }

}
