package ru.practicum.ewm.rating.model;

import ru.practicum.ewm.rating.dto.RatingState;
import ru.practicum.ewm.events.model.Event;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.EnumType;
import lombok.AllArgsConstructor;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private RatingState ratingState;
}
