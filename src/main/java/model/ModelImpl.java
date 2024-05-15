package model;

public class ModelImpl implements Model {

    private User user;

    @Override
    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return this.user;
    }
    
}
