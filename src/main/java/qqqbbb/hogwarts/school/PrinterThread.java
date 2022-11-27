package qqqbbb.hogwarts.school;

import java.util.ArrayList;
import java.util.List;

public class PrinterThread extends Thread
{
    List<String> strings = new ArrayList<>();

    private Object syncObj = null;

    @Override
    public void run()
    {
        if (syncObj == null)
            print();
        else
            printSync();
    }

    public void print()
    {
        for (String name : strings)
        {
            if (name == null || name.isEmpty())
                continue;

            System.out.println(this.getName() + " print " + name);
        }
    }

    public void printSync()
    {
        synchronized (syncObj)
        {
            for (String name : strings)
            {
                if (name == null || name.isEmpty())
                    continue;

                System.out.println(this.getName() + " printSync " + name);
            }
        }
    }

    public void setStrings(List<String>  s)
    {
        strings = new ArrayList<>(s);
    }

    public void setSyncObj(Object o)
    {
        syncObj = o;
    }

}
