package pl.agh.dp.loadbalancer.DataBaseInstance;

import lombok.RequiredArgsConstructor;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabaseState;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBases;

@RequiredArgsConstructor
public class DBInstanceImpl implements DatabaseInstance{

    private final DataBases dataBases;



    @Override
    public boolean sendQuery(String query) {
        return false;
    }

    @Override
    public DatabaseState getState() {
        return null;
    }

    @Override
    public void actualiseState() {

    }

    @Override
    public double getLoad() {
        return 0;
    }


}
