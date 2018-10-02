public class Calculator {


    // Indicele de masa corporala

    public double imcCalculator(double inaltimea, double masa) {
        double imc = masa / ((inaltimea * inaltimea) / 10000);

        imc = Math.floor(imc * 100) / 100; // reducem precizia variabilei

        return imc;
    }

    public double miCalculator(double inaltimea) {

        double masa = (inaltimea - 100) - ((inaltimea - 100) * (double) (10 / 100));

        masa = Math.floor(masa * 100) / 100;

        return masa;
    }

    // Necesarul caloric in functie de activitate

    public double ncCalculator (String tip, double rmb){

        double nc = 0;

        if (tip.equals("sed")){

            nc = rmb * 1.2;
        }
        else if (tip.equals("usor")){

            nc = rmb * 1.375;
        }
        else if (tip.equals("mod")){

            nc = rmb * 1.55;
        }
        else if(tip.equals("ints")){

            nc = rmb * 1.725;
        }
        else if (tip.equals("fri")){

            nc = rmb * 1.9;
        }

        nc = Math.floor(nc * 100) / 100;

        return  nc;
    }

    // Rata metabolismului bazal

    public double rmbCalculator(double varsta, double masa, double inaltimea, String sex) {

        double rmb = 0;

        if (sex.equals("b")) {

            rmb = 66 + (13.7 * masa) + (5 * inaltimea) - (6.8 * varsta);
        }
        else if (sex.equals("f")){

            rmb = 655 + (9.6 * masa) + (1.8 * inaltimea) - (4.7 * varsta);

        }
        rmb = Math.floor(rmb * 100) / 100;

        return rmb;

    }
}
