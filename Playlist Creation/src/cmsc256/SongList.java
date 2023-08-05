package cmsc256;

import java.io.IOException;
import java.util.*;

import bridges.base.SLelement;
import bridges.connect.Bridges;
import bridges.connect.DataSource;
import bridges.data_src_dependent.Song;

import java.lang.Iterable;



//Braeden Ferguson
//2/26/2023
//Project3
//CMSC256-901
//this code combines Linked lists with generic class and iterators to create a music playlist and sort through by arist.

public class SongList implements List<MySong>, Iterable<MySong> {
    private String playlistName;


    private SLelement<MySong> head;      // Pointer to list header
    private SLelement<MySong> tail;      // Pointer to last element
    private SLelement<MySong> curr;      // Access to current element
    private int listSize;      // Size of list


    public SongList() {
        playlistName = "";
        listSize = 0;
        head = null;
        tail = null;

    }

    public SongList(String aPlaylistName) {
        playlistName = aPlaylistName;
        listSize = 0;
        head = null;
        tail = null;

    }




    public String getSongsByArtist(String artist) throws IllegalArgumentException{
        if (artist == null) {
            throw new IllegalArgumentException();
        }
        String result = "";
        ArrayList<MySong> songsByArtist = new ArrayList<>();
        SongIterator songIterator = new SongIterator();

        while (songIterator.hasNext()) {
            MySong song = songIterator.next();
            if (song.getArtist().equalsIgnoreCase(artist)) {
                songsByArtist.add(song);
            }
        }
        if (songsByArtist.size() == 0) {
           result = "There are no songs by " + artist + " in this playlist.";
        } else {
            //sort arraryList
            Collections.sort(songsByArtist);
            for (int i = 0; i < songsByArtist.size(); i++) {
                MySong s = songsByArtist.get(i);
                result += "Title: " + s.getSongTitle() + "   Album: " + s.getAlbumTitle();

                // Check if it's the last entry
                if (i != songsByArtist.size() - 1) {
                    result += "\n";
                }
            }
        }
        return result;
    }




    public void clear(){
        head = null;
        tail = null;// Create header, trailer, reset curr to head
        listSize = 0;
    }


    public boolean insert(MySong song, int position) throws NoSuchElementException {
        if (position < 0 || position > listSize) {
            throw new IndexOutOfBoundsException("Index " + position + " is out of bounds for this playlist");
        }
        SLelement<MySong> newSong = new SLelement<MySong>(song.getSongTitle(), song);
        if (position == 0) {
            newSong.setNext(head);
            head = newSong;
            if (tail == null) {
                tail = newSong;
            }
        } else if (position == listSize) {
            tail.setNext(newSong);
            tail = newSong;
        } else {//this needs to be fixed
            SLelement<MySong> prev = head;
            for (int i = 0; i < position - 1; i++) {
                prev = prev.getNext();
            }
            SLelement<MySong> next = prev.getNext();
            prev.setNext(newSong);
            newSong.setNext(next);
        }
        listSize++;
        return true;}

    public boolean add(MySong song){
        SLelement<MySong> newSong = new SLelement<MySong>(song.getSongTitle(), song);
        if (head == null) {
            head = newSong;
        }else {
            tail.setNext(newSong);
        }
        tail = newSong;
        listSize++;
        return true;
    }



    public int size(){return listSize;}


    public boolean isEmpty(){return listSize == 0;}


    public boolean contains(MySong target) throws IllegalArgumentException{
        if (target == null) {
            throw new IllegalArgumentException();
        }
        System.out.println("Target Song: " + target.getSongTitle());
        Iterator<MySong> iterator = this.iterator();
        while (iterator.hasNext()) {
            MySong song = iterator.next();
            if (song.equals(target)) {
                System.out.println("Song found");
                return true;
            }
        }
        System.out.println("Song not found");
        return false;
    }

    public MySong remove(int position) throws IndexOutOfBoundsException {
        if (position < 0 || position >= listSize) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        MySong removedSong;

        if (position == 0) {
            removedSong = head.getValue();

            if (head.getNext() == null) {
                head = null;
                tail = null;
            }
        }
        else {//this needs to be fixed
            SLelement<MySong> prev = head;
            for (int i = 0; i < position - 1; i++) {
                prev = prev.getNext();
            }
            SLelement<MySong> current = prev.getNext();
            removedSong = current.getValue();
            prev.setNext(current.getNext());
            if (current.getNext() == null) {
                tail = prev;
            }
        }

        listSize--;
        return removedSong;
    }









    public MySong getValue(int position) throws IndexOutOfBoundsException {
        if (position < 0 || position >= listSize) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }
        if (isEmpty()) {
            return null;
        }

        SLelement<MySong> currentNode = head;
        int currentPosition = 0;

        while (currentPosition != position) {
            currentNode = currentNode.getNext();
            currentPosition++;
        }

        return currentNode.getValue();
    }

    @Override
    public Iterator<MySong> iterator() {
        return new SongIterator();
    }


    private class SongIterator implements Iterator<MySong> {

        bridges.base.SLelement<MySong> curr;
        int currentIndex;



        public SongIterator() {
            curr = head;
            currentIndex = 0;
        }


            public boolean hasNext() {
            return curr != null;
        }

        @Override
        public MySong next() throws NoSuchElementException{
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            MySong result = curr.getValue();
            curr = curr.getNext();
            currentIndex++;
            return result;
        }


    }

public String getPlaylistName() {
        return playlistName;
}


    public static void main (String[] args) throws IOException {


        Bridges bridges = new Bridges(3, "fergusonba", "757857963875");

        DataSource ds = bridges.getDataSource();
        ArrayList<Song> allSongs = ds.getSongData();


        SongList aSongList = new SongList("MySongList");

        for (Song s: allSongs) {
            aSongList.add(new MySong(s));
        }
        ArrayList<MySong> testList = new ArrayList<>();
        Iterator<MySong> fullList = aSongList.iterator();
        while (fullList.hasNext()) {
            MySong curSong = fullList.next();
            testList.add(curSong);
        }
        Collections.sort(testList);
        for (MySong v: testList) {
            System.out.println(v.getSongTitle() + ", " + v.getArtist());
        }
        //test cases for contains, and getSongsByArtist
        MySong testSong1 = null;
        Iterator<MySong> iterator = aSongList.iterator();

        while (iterator.hasNext()) {
            MySong song = iterator.next();
            if (song.getSongTitle().equals("One")) {
                testSong1 = song;
                break;
            }
        }
        MySong testSong2 = null;
        Iterator<MySong> iterator2 = aSongList.iterator();

        while (iterator2.hasNext()) {
            MySong song = iterator2.next();
            if (song.getSongTitle().equals("Ones")) {
                testSong2 = song;
                break;
            }
        }

        MySong testSong3 = null;
        Iterator<MySong> iterator3 = aSongList.iterator();

        while (iterator3.hasNext()) {
            MySong song = iterator3.next();
            if (song.getSongTitle().equals("Enter Sandman")) {
                testSong3 = song;
                break;
            }
        }
        if (testSong1 == null) {
            testSong1 = new MySong();
        }
        if (testSong2 == null) {
            testSong2 = new MySong();
        }
        if (testSong3 == null) {
            testSong3 = new MySong();
        }
        if (testSong1 != null) {
            System.out.println(aSongList.contains(testSong1));
        }
        if (testSong2 != null) {
            System.out.println(aSongList.contains(testSong2));
        }

        if (testSong3 != null) {
            System.out.println(aSongList.contains(testSong3));
        }


        System.out.println("\n\n" + aSongList.getSongsByArtist("Olivia Rodrigo"));

        System.out.println(aSongList.getSongsByArtist("Chief"));

        System.out.println(aSongList.getSongsByArtist("sdfjasdlfkadjskfldajsklf"));

        System.out.println("\n" + aSongList.getSongsByArtist("cHiEF kEEf"));

    }
        }






