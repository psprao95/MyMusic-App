import java.util.ArrayList;

public class Album {

	private int id;
	private String name;
	private String imagePath;
	private int year;
	private String genre;
	private float imdbRating;
	private ArrayList<Track> tracks;

	Album(String name, String imagePath, int year,String genre, float imdbRating)
	{
		this.name=name;
		this.imagePath=imagePath;
		this.year=year;
		this.genre=genre;
		this.imdbRating=imdbRating;
	}

	public void addTrack(Track track)
	{
		this.tracks.add(track);
	}

	public int  getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public String getImagePath()
	{
		return this.imagePath;
	}

	public String getGenre()
	{
		return this.genre;
	}

	public  int getYear()
	{
		return this.year;
	}

	public float getImdbRating()
	{
		return this.imdbRating;
	}


	public void setName(String name)
	{
		this.name=name;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath=imagePath;
	}

	public void setGenre(String genre)
	{
		this.genre=genre;
	}

	public void setYear(int year)
	{
		this.year=year;
	}

	public void setImdbRating(float imdbRating)
	{
		this.imdbRating=imdbRating;
	}

}
