package corbaBanque;


public final class tabComptesHolder implements org.omg.CORBA.portable.Streamable
{
    public corbaBanque.Compte value[] = null;

    public tabComptesHolder ()
    {
    }

    public tabComptesHolder (corbaBanque.Compte[] initialValue)
    {
        value = initialValue;
    }

    public void _read (org.omg.CORBA.portable.InputStream i)
    {
        value = corbaBanque.tabComptesHelper.read (i);
    }

    public void _write (org.omg.CORBA.portable.OutputStream o)
    {
        corbaBanque.tabComptesHelper.write (o, value);
    }

    public org.omg.CORBA.TypeCode _type ()
    {
        return corbaBanque.tabComptesHelper.type ();
    }

}
