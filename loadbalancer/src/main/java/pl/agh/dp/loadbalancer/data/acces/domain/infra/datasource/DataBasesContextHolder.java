package pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DataBasesContextHolder {
    private static ThreadLocal<DataBases> threadLocal;
    public DataBasesContextHolder() {
        threadLocal = new ThreadLocal<>();
    }
    public void setBranchContext(DataBases dataBase) {
        threadLocal.set(dataBase);
    }
    public DataBases getBranchContext() {
        return threadLocal.get();
    }

    public static void clearBranchContext() {
        threadLocal.remove();
    }

}
