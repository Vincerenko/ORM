package orm.repositories;

import orm.entities.Firm;
import service.orm.OrmManager;

public class FirmRepository extends OrmManager<Firm, Integer> {
    public FirmRepository() {
        super(Firm.class, Integer.class);
    }
}
