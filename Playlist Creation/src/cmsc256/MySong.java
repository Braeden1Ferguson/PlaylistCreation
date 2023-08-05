package cmsc256;

import bridges.data_src_dependent.Song;

public class MySong extends bridges.data_src_dependent.Song implements  Comparable<MySong> {



    public MySong() {
        super();
    }

    public MySong(String artist, String song, String album, String lyrics, String release_date) {
        super(artist, song, album, lyrics, release_date);
    }

    public MySong(Song song) {
        super(song.getArtist(), song.getSongTitle(), song.getAlbumTitle(), song.getLyrics(), song.getReleaseDate());

    }


    @Override
    public int compareTo(MySong o) throws NullPointerException {
        if (o == null) {
            throw new NullPointerException();
        }
        return this.getSongTitle().compareToIgnoreCase(o.getSongTitle());
            }

            }












