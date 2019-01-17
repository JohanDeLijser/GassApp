package nl.gassapp.gassapp.Utils;

import android.content.Context;

public class SqlUtil {

    private static SqlUtil instance = null;

    private SqlUtil(Context context)
    {



    }

    public static synchronized SqlUtil getInstance(Context context)
    {

        if (instance == null) {

            instance = new SqlUtil(context);

        }

        return instance;

    }

    public static synchronized SqlUtil getInstance()
    {
        if (instance == null)
        {
            throw new IllegalStateException(SqlUtil.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }



}
