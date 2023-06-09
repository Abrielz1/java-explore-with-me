package ru.practicum.ewm.events.model;

import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.user.model.User;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.EnumType;
import lombok.AllArgsConstructor;
import javax.persistence.Column;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EVENTS")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private Boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_state")
    private EventState eventState;

    private String title;
}
