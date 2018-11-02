package Technical;

public class Spawner {
	String Name;
	int x; 
	int y;
	boolean destructible;
	int spawningRate; //Cooldown time (in s) between mobs
	int spawningRadius; //in blocks; 1 -> 10
	float health;
	int type; //0=passive 1=friendly 2=aggressive
	int stamina;
	float velocity;
	int dropID;
}
