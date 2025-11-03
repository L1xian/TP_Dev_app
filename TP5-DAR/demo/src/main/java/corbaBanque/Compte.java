package corbaBanque;

public final class Compte implements org.omg.CORBA.portable.IDLEntity
{
    public int code = (int)0;
    public float solde = (float)0;

    public Compte (){}

    public Compte (int _code, float _solde)
    {
        code = _code;
        solde = _solde;
    }

}
