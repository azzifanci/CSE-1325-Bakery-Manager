public class Customer
{
    private int customerID;
    private String name;
    private String address;
    private String phoneNumber;
    private String emailAddress;

    public Customer(int customerID, String name, String address, String phoneNumber, String emailAddress)
    {
        this.customerID= customerID;
        this.setName(name);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
        this.setEmailAddress(emailAddress);
    }

    public Customer()
    {
        this.customerID= getCustomerID();
        this.name= getName();
        this.address= getAddress();
        this.phoneNumber= getPhoneNumber();
        this.emailAddress= getEmailAddress();
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

    //Fixed the setters so will throw an exception rather than have a customer with no information
    public void setName(String aName)
    {
        if(aName == null || aName.isBlank())
        {
            throw new IllegalArgumentException("Customer name is required.");
        }
        else
        {
            this.name = aName;
        }
    }
    public void setAddress(String aAddress)
    {
        if(aAddress == null || aAddress.isBlank())
        {
            throw new IllegalArgumentException("Address is required.");
        }
        else
        {
            this.address = aAddress;
        }
    }
    public void setPhoneNumber(String aPhoneNumber)
    {
        if(aPhoneNumber == null || aPhoneNumber.isBlank())
        {
            throw new IllegalArgumentException("Phone number is required.");
        }
        else
        {
            this.phoneNumber = aPhoneNumber;
        }
    }
    public void setEmailAddress(String aEmailAddress)
    {
        if(aEmailAddress == null || aEmailAddress.isBlank())
        {
            throw new IllegalArgumentException("Email address is required.");
        }
        else
        {
            this.emailAddress = aEmailAddress;
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
