package pl.agh.dp.loadbalancer.Club;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity(name = "ClubEntity")
@Getter
@Setter
@Table(name = "CLUBS_TABLE")
public class ClubEntity {


    public ClubEntity(Object[] object) {
        this.club_name = (String) object[0];
        this.city = (String) object[1];
        this.foundation_date = (Date) object[2];
        this.club_id = Long.valueOf((int) object[3]);
        this.province = (String) object[4];
    }

    public ClubEntity(String name, String city, Date date, Long id, String province) {
        this.club_name = name;
        this.city = (String) city;
        this.foundation_date = date;
        this.club_id = id;
        this.province = province;
    }

    public ClubEntity() {
        this.club_name = null;
        this.city = null;
        this.foundation_date = null;
        this.club_id = null;
        this.province = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    Long club_id;

    @Column(name = "club_name")
    String club_name;

    @Column(name = "city")
    String city;

    @Column(name = "foundation_date")
    Date foundation_date;

    @Column(name = "province")
    String province;

    @Override
    public String toString() {
        return "Club{" +
                "clubName='" + club_name + '\'' +
                ", date='" + foundation_date + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                '}';
    }

    public ClubEntity(ClubEntity club) {
        this.club_id = club.club_id;
        this.club_name =  club.club_name;
        this.city =  club.city;
        this.foundation_date =  club.foundation_date;
        this.province =  club.province;
    }
}
