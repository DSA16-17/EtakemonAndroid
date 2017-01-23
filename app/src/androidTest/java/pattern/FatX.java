package pattern;

import java.util.ArrayList;
import java.util.List;

import dsa.eetac.upc.edu.etakemon.Patterns;

/**
 * Created by Turpitude on 05/01/2017.
 */

public class FatX implements Patterns {

    List<Boolean> pattern = new ArrayList<Boolean>(){{

        add(true);
        add(false);
        add(false);
        add(true);
        add(true);
        add(true);
        add(true);
        add(true);
        add(false);
        add(true);
        add(true);
        add(false);
        add(true);
        add(true);
        add(true);
        add(true);
        add(false);
        add(true);
        add(true);
        add(false);
        add(true);
        add(true);
        add(true);
        add(true);
        add(true);
        add(false);
        add(false);
        add(true);

    }};

    @Override
    public List<Boolean> setPattern() {
        return pattern;
    }
}
