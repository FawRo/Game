package fateme.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private String name;
    private List<Card> cards;
    private int cards_number;
    private int room_number;
    private List<Place> places;
    private List<Person> persons;
    private List<Room> rooms;

    public Player(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
        this.cards_number = cards.size();
        this.room_number = 1;
        this.places = new ArrayList<>();
        this.persons = new ArrayList<>();
        this.rooms = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Place) {
                places.add((Place) card);
            } else if (card instanceof Person) {
                persons.add((Person) card);
            } else if (card instanceof Room) {
                rooms.add((Room) card);
            }
        }

    }

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
        this.cards_number = 0;
        this.places = new ArrayList<>();
        this.persons = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
        cards_number++;
        addCard_to_data(card);
    }

    public void addCard_to_data(Card card) {
        if (card instanceof Place) {
            places.add((Place) card);
        } else if (card instanceof Person) {
            persons.add((Person) card);
        } else if (card instanceof Room) {
            rooms.add((Room) card);
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getCards_number() {
        return cards_number;
    }

    public int getRoomNumber() {
        return room_number;
    }

    public int have_this_card(Card card) {
        if (card instanceof Place) {
            for (Place place : places) {
                if (place.getName().equals(card.getName())) {
                    return 1;
                }
            }
        } else if (card instanceof Person) {
            for (Person person : persons) {
                if (person.getName().equals(card.getName())) {
                    return 1;
                }
            }
        } else if (card instanceof Room) {
            for (Room room : rooms) {
                if (room.getName().equals(card.getName())) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public int is_valid_room(int newRoom, int tas1, int tas2) {
        if (newRoom == room_number) {
            return 0;
        }
        // frad be khane fard
        // zoj be khane zoj
        if ((tas1 + tas2) % 2 != newRoom % 2)
            return 0;

        if (room_number == 2 && (newRoom == 3 || newRoom == 9))
            return 0;

        if (room_number == 3 && (newRoom == 2 || newRoom == 4))
            return 0;

        if (room_number == 4 && (newRoom == 3 || newRoom == 5))
            return 0;

        if (room_number == 5 && (newRoom == 4 || newRoom == 6))
            return 0;

        if (room_number == 6 && (newRoom == 5 || newRoom == 7))
            return 0;

        if (room_number == 7 && (newRoom == 6 || newRoom == 8))
            return 0;

        if (room_number == 8 && (newRoom == 7 || newRoom == 9))
            return 0;

        if (room_number == 9 && (newRoom == 8 || newRoom == 2))
            return 0;

        return 1;
    }

    public void ask(int tas1, int tas2, List<Room> allrooms_, List<Person> allpersons_, List<Place> allplaces_,
            List<Card> newCards) {
        System.out.println("tas1 : " + tas1);
        System.out.println("tas2 : " + tas2);

        List<Room> allrooms = new ArrayList<>(allrooms_.subList(0, allrooms_.size()));
        List<Person> allpersons = new ArrayList<>(allpersons_.subList(0, allpersons_.size()));
        List<Place> allplaces = new ArrayList<>(allplaces_.subList(0, allplaces_.size()));

        Collections.shuffle(allrooms, new Random());
        Collections.shuffle(allpersons, new Random());
        Collections.shuffle(allplaces, new Random());

        Room newRoom = null;
        for (Room room : allrooms) {
            if (is_valid_room(room.getN(), tas1, tas2) == 1 && have_this_card(room) == 0) {
                newRoom = room;
                break;
            }
        }
        if (newRoom == null) {
            for (Room room : allrooms) {
                if (is_valid_room(room.getN(), tas1, tas2) == 1) {
                    newRoom = room;
                    break;
                }
            }
        }

        Person newPerson = null;
        for (Person person : allpersons) {
            if (have_this_card(person) == 0) {
                newPerson = person;
                break;
            }
        }
        if (newPerson == null) {
            newPerson = allpersons.get(0);
        }

        Place newPlace = null;
        for (Place place : allplaces) {
            if (have_this_card(place) == 0) {
                newPlace = place;
                break;
            }
        }
        if (newPlace == null) {
            newPlace = allplaces.get(0);
        }

        System.out.println(name + " : I select this cards");
        System.out.println("new room : " + newRoom.getName());
        System.out.println("new person : " + newPerson.getName());
        System.out.println("new place : " + newPlace.getName());
        newCards.add(newRoom);
        newCards.add(newPerson);
        newCards.add(newPlace);

    }

    public void ask(int tas1, int tas2, List<Room> allrooms, List<Person> allpersons, List<Place> allplaces,
            List<Card> newCards, Scanner scanner) {
        System.out.println("tas1 : " + tas1);
        System.out.println("tas2 : " + tas2);

        Room newRoom = null;
        int i = 1;
        System.out.println("Room : ");
        for (Room room : allrooms) {
            System.out.print(i + ". " + room.getName() + "\t");
            i++;
        }
        int room_i = 0;
        while (true) {
            System.out.print("\nselect a room : ");
            room_i = scanner.nextInt();
            if (room_i > 0 && room_i < 10 && is_valid_room(room_i, tas1, tas2) == 1) {
                break;
            } else {
                System.out.println("please enter a valid number");
            }
        }

        Person newPerson = null;
        int person_i = 0;
        System.out.println("Person : ");
        i = 1;
        for (Person person : allpersons) {
            System.out.print(i + ". " + person.getName() + "\t");
            i++;
        }
        while (true) {
            System.out.print("\nselect a person :");
            person_i = scanner.nextInt();
            if (person_i > 0 && person_i < 7) {
                break;
            } else {
                System.out.println("please enter a valid number");
            }
        }

        Place newPlace = null;
        int place_i = 0;
        System.out.println("Place : ");
        i = 1;
        for (Place place : allplaces) {
            System.out.print(i + ". " + place.getName() + "\t");
            i++;
        }
        while (true) {
            System.out.print("\nselect a place : ");
            place_i = scanner.nextInt();
            if (place_i > 0 && place_i < 7) {
                break;
            } else {
                System.out.println("please enter a valid number");
            }
        }

        newRoom = allrooms.get(room_i - 1);
        newPerson = allpersons.get(person_i - 1);
        newPlace = allplaces.get(place_i - 1);

        System.out.println(name + " : I select this cards");
        System.out.println("new room : " + newRoom.getName());
        System.out.println("new person : " + newPerson.getName());
        System.out.println("new place : " + newPlace.getName());

        newCards.add(newRoom);
        newCards.add(newPerson);
        newCards.add(newPlace);
        room_number = newRoom.getN();

    }

    public int final_gues(List<Room> allrooms, List<Person> allpersons, List<Place> allplaces, List<Card> newCards,
            Davar davar) {
        int room_n = rooms.size();
        int person_n = persons.size();
        int place_n = places.size();
        if (room_n == 8 && person_n == 5 && place_n == 5) {

            for (Room room : allrooms) {
                if (have_this_card(room) == 0) {
                    newCards.add(room);
                    break;
                }
            }
            for (Person person : allpersons) {
                if (have_this_card(person) == 0) {
                    newCards.add(person);
                    break;
                }
            }
            for (Place place : allplaces) {
                if (have_this_card(place) == 0) {
                    newCards.add(place);
                    break;
                }
            }
            if (davar.is_correct((Room) newCards.get(0), (Person) newCards.get(1), (Place) newCards.get(2)) == 1) {
                System.out.println(davar.toString());
                System.out.println("room : " + newCards.get(0).getName());
                System.out.println("person : " + newCards.get(1).getName());
                System.out.println("place : " + newCards.get(2).getName());
                return 1;
            }

            return 0;
        }
        return 0;

    }

    public int final_gues(List<Room> allrooms, List<Person> allpersons, List<Place> allplaces, Davar davar,
            Scanner scanner) {

        int i = 1;
        System.out.println("Room : ");
        for (Room room : allrooms) {
            System.out.print(i + ". " + room.getName() + "\t");
            i++;
        }
        int room_i = 0;
        while (true) {
            System.out.print("\nselect a room : ");
            room_i = scanner.nextInt();
            if (room_i > 0 && room_i < 10) {
                break;
            } else {
                System.out.println("please enter a valid number");
            }
        }

        int person_i = 0;
        System.out.println("Person : ");
        i = 1;
        for (Person person : allpersons) {
            System.out.print(i + ". " + person.getName() + "\t");
            i++;
        }
        while (true) {
            System.out.print("\nselect a person :");
            person_i = scanner.nextInt();
            if (person_i > 0 && person_i < 7) {
                break;
            } else {
                System.out.println("please enter a valid number");
            }
        }

        int place_i = 0;
        System.out.println("Place : ");
        i = 1;
        for (Place place : allplaces) {
            System.out.print(i + ". " + place.getName() + "\t");
            i++;
        }
        while (true) {
            System.out.print("\nselect a place : ");
            place_i = scanner.nextInt();
            if (place_i > 0 && place_i < 7) {
                break;
            } else {
                System.out.println("please enter a valid number");
            }
        }
        Room newRoom = allrooms.get(room_i - 1);
        Person newPerson = allpersons.get(person_i - 1);
        Place newPlace = allplaces.get(place_i - 1);

        if (davar.is_correct(newRoom, newPerson, newPlace) == 1) {
            System.out.println("you win");
            System.out.println(davar.toString());
            System.out.println("room : " + newRoom.getName());
            System.out.println("person : " + newPerson.getName());
            System.out.println("place : " + newPlace.getName());
            return 1;
        }
        System.out.println("you lose");
        return 0;
    }

    public int answer(List<Card> newCards, Player other) {
        Room newRoom = (Room) newCards.get(0);
        Person newPerson = (Person) newCards.get(1);
        Place newPlace = (Place) newCards.get(2);

        Card showCard = null;
        for (Card card : cards) {
            if (card.getName().equals(newRoom.getName()) || card.getName().equals(newPerson.getName())
                    || card.getName().equals(newPlace.getName())) {
                if (other.have_this_card(card) == 0) {
                    showCard = card;
                    other.addCard_to_data(showCard);
                    break;
                }
            }
        }
        if (showCard == null) {
            System.out.println(name + " : I don't have any of these cards");

            return 0;
        }
        System.out.println(name + " : I have this card : " + showCard.getName());
        return 1;
    }

    public String getName() {
        return name;
    }

    public String show_data() {
        String s = "";
        s += "rooms : \n";
        for (Room room : rooms) {
            s += room.getName() + "\t";
        }
        s += "\n------------------\n";
        s += "persons : \n";
        for (Person person : persons) {
            s += person.getName() + "\t";
        }
        s += "\n------------------\n";
        s += "places : \n";
        for (Place place : places) {
            s += place.getName() + "\t";
        }
        s += "\n===================================\n\n";
        return s;
    }

    public String toString() {
        String s = "";
        s += "name : " + name + ":\n";
        s += "card number : " + cards_number + "\n";
        s += "cards : \n";
        for (Card card : cards) {
            s += card.getName() + "\t";
        }
        s += "\n------------------\n";
        s += "rooms : \n";
        for (Room room : rooms) {
            s += room.getName() + "\t";
        }
        s += "\n------------------\n";
        s += "persons : \n";
        for (Person person : persons) {
            s += person.getName() + "\t";
        }
        s += "\n------------------\n";
        s += "places : \n";
        for (Place place : places) {
            s += place.getName() + "\t";
        }
        s += "\n===================================\n\n";
        return s;

    }

}
