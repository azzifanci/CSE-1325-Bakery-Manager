public class Customer
{
    private int customerID;
    private String name;
    private String address;
    private String phoneNumber;
    private String emailAddress;

    public Customer(int customerID, String name, String address, String phoneNumber, String emailAddress)
    {
        this.setCustomerID(customerID);
        this.setName(name);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
        this.setEmailAddress(emailAddress);
    }
    public int getCustomerID()
    {
        return this.customerID;
    }
    public String getName()
    {
        return this.name;
    }
    public String getAddress()
    {
        return this.address;
    }
    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }
    public String getEmailAddress()
    {
        return this.emailAddress;
    }

    public void setCustomerID(int aCustomerID)
    {
        if(aCustomerID> 0 && aCustomerID< 999999)
        {
            this.customerID= aCustomerID;
        }else{
            this.customerID= 000001;
        }
    }
    public void setName(String aName)
    {
        if(aName !=null)
        {
            this.name= aName;
        }else {
            this.name= "none";
        }
    }
    public void setAddress(String aAddress)
    {
        if(aAddress != null)
        {
            this.address = aAddress;
        }else{
            this.address= "Store address: 1234 Berry Lane, Arlington.";
        }
    }
    public void setPhoneNumber(String aPhoneNumber)
    {
        if(aPhoneNumber !=null)
        {
            this.phoneNumber = aPhoneNumber;
        }else {
            this.phoneNumber= "None";
        }
    }
    public void setEmailAddress(String aEmailAddress)
    {
        if(aEmailAddress !=null)
        {
            this.emailAddress = aEmailAddress;
        }else {
            this.emailAddress= "None";
        }
    }

    public String toString()
    {
        return this.customerID+ " "+ this.name+ " "+ this.address+ " "+ this.phoneNumber+ " "+ this.emailAddress;
    }
    public boolean equals(Customer aCustomer)
    {
        return aCustomer !=null && this.customerID== aCustomer.getCustomerID() &&
                this.name.equalsIgnoreCase(aCustomer.getName()) &&
                this.address.equals(aCustomer.getAddress()) &&
                this.phoneNumber.equals(aCustomer.getPhoneNumber()) &&
                this.emailAddress.equals(aCustomer.getEmailAddress());
    }
}
