package corbaBanque;


public final class CompteHolder implements org.omg.CORBA.portable.Streamable
{
    public corbaBanque.Compte value = null;

    public CompteHolder ()
    {
    }

    public CompteHolder (corbaBanque.Compte initialValue)
    {
        value = initialValue;
    }

    public void _read (org.omg.CORBA.portable.InputStream i)
    {
        value = corbaBanque.CompteHelper.read (i);
    }

    public void _write (org.omg.CORBA.portable.OutputStream o)
    {
        corbaBanque.CompteHelper.write (o, value);
    }

    public org.omg.CORBA.TypeCode _type ()
    {
        return corbaBanque.CompteHelper.type ();
    }

}
