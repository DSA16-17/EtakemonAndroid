package pattern;

import java.util.ArrayList;
import java.util.List;

import dsa.initial.Patterns;

/**
 * Created by Turpitude on 05/01/2017.
 */

public class BigO implements Patterns {
    List<Boolean> pattern = new ArrayList<Boolean>(){{
        add(false);
        add(true);
        add(true);
        add(false);

        add(true);
        add(false);
        add(false);
        add(true);

        add(true);
        add(false);
        add(false);
        add(true);

        add(true);
        add(false);
        add(false);
        add(true);

        add(true);
        add(false);
        add(false);
        add(true);


        add(true);
        add(false);
        add(false);
        add(true);

        add(false);
        add(true);
        add(true);
        add(false);

    }};

    @Override
    public List<Boolean> setPattern() {
        return pattern;
    }

    @Override
    public String getClassName() {
        return this.getClass().getName();
    }
}


