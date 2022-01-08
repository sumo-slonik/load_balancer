package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBases;

@RequiredArgsConstructor
public class DBInstanceImpl implements DatabaseInstance{

    private final DataBases dataBases;
    private DataBaseState state;


    @Override
    public boolean sendQuery(String query) {
        return false;
    }

    @Override
    public DataBaseStates getState() {
        return this.state.getState();
    }

    @Override
    public Double getLoad() {
        return null;
    }
}
