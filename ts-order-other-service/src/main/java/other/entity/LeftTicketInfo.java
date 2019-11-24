package other.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class LeftTicketInfo {
    @Valid
    @NotNull
    private Set<Ticket> soldTickets;


    public LeftTicketInfo()  {
        //dafault constructor
    }

    public Set<Ticket> getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(Set<Ticket> soldTickets) {
        this.soldTickets = soldTickets;
    }

    @Override
    public String toString() {
        return "LeftTicketInfo{" +
                "soldTickets=" + soldTickets +
                '}';
    }
}
