package Salomax.assignedClients;

import Salomax.client.Client;
import Salomax.studio.Studio;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "assignedClients")
@Data
public class AssignedClients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studio_fk")
    private Studio studio;

    @ManyToOne
    @JoinColumn(name = "client_fk")
    private Client client;

}