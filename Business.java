import java.io.Serializable;

public class Business implements Serializable
{
    private static final long serialVersionUID = 1477743064500009088L;

    private final String      BN_NAME;
    private final String      BN_STATUS;
    private final String      BN_REG_DT;
    private final String      BN_CANCEL_DT;
    private final String      BN_RENEW_DT;
    private final String      BN_STATE_NUM;
    private final String      BN_STATE_OF_REG;
    private final Long        BN_ABN;

    public Business(String BN_NAME, String BN_STATUS, String BN_REG_DT, String BN_CANCEL_DT,
                    String BN_RENEW_DT, String BN_STATE_NUM, String BN_STATE_OF_REG, Long BN_ABN)
    {
        this.BN_NAME = BN_NAME;
        this.BN_STATUS = BN_STATUS;
        this.BN_REG_DT = BN_REG_DT;
        this.BN_CANCEL_DT = BN_CANCEL_DT;
        this.BN_RENEW_DT = BN_RENEW_DT;
        this.BN_STATE_NUM = BN_STATE_NUM;
        this.BN_STATE_OF_REG = BN_STATE_OF_REG;
        this.BN_ABN = BN_ABN;
    }

    @Override
    public String toString()
    {
        return "\n|Name: " + BN_NAME 
             + "\n|Status: " + BN_STATUS
             + "\n|Registered: " + BN_REG_DT
             + "\n|Cancelled: " + BN_CANCEL_DT
             + "\n|Renewed: " + BN_RENEW_DT
             + "\n|State number: " + BN_STATE_NUM
             + "\n|State of registration: " + BN_STATE_OF_REG
             + "\n|ABN: " + BN_ABN;
    }

    public String getBusinessName()
    {
        return BN_NAME.toLowerCase();
    }
}
