package com.gmail.andrewahughes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MyGdxGame extends ApplicationAdapter  implements  StageInterface{
	private final float UPDATE_TIME = 1/60f;
	float timer;
	SpriteBatch batch;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	private Socket socket;
	String id;
	Starship player;
	Texture playerShip;
	Texture friendlyShip;

	TitleStage titleStage;
	OptionsStage optionsStage;
	MatchMakingStage matchMakingStage;
	DealStage dealStage;
	TridentBuildingStage tridentBuildingStage;
	GameStage gameStage;
	GameOverStage gameOverStage;
	/*the hashmap will store the a key which in our case is a string
	 and a value which for us is a Starship class
	* string will store the id , starship will store a starship class*/
	HashMap<String, Starship> friendlyPlayers;

	@Override
	public void create() {

		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		playerShip = new Texture("badlogic.jpg");
		friendlyShip = new Texture("badlogic.jpg");
		friendlyPlayers = new HashMap<String, Starship>();

		/*pass in this (MyGdxGame) as the stage interface so the stage can use the stage interface methods*/
		titleStage = new TitleStage(this);
		optionsStage = new OptionsStage(this);
		matchMakingStage = new MatchMakingStage(this);
		dealStage = new DealStage(this);
		tridentBuildingStage = new TridentBuildingStage(this);
		gameStage = new GameStage(this);
		gameOverStage = new GameOverStage(this);

		goToStage(TITLESTAGE);
		connectSocket();
		configSocketEvents();
	}
	public void handleInput(float dt){
		if(player != null) {
			if (Gdx.input.isTouched()) {

				player.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());
			}
		}
	}
	/*send an update to the server*/
	public void updateServer(float dt){
		timer+=dt;
		if(timer>= UPDATE_TIME && player!= null&& player.hasMoved()){
			JSONObject data = new JSONObject();
			try{
				data.put("x",player.getX());
				data.put("y",player.getY());
				/*the player emits data to the server here
				* the server has a corresponding method to
				* interpret this data*/
				socket.emit("playerMoved",data);

			} catch(JSONException e) {
				Gdx.app.log("SOCKET.IO","Error sending update data");
			}

		}

	}
	@Override
	public void render() {
		handleInput(Gdx.graphics.getDeltaTime());
		updateServer(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(0,
				0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				Color.RED, Color.GREEN, Color.BLUE, Color.WHITE);
		shapeRenderer.end();
		batch.begin();
		if(player != null){
			player.draw(batch);
			font.draw(batch,"[bottomleft] x: "+player.getX()+" y: "+player.getY(),10,20);
			font.draw(batch," [topleft] x: "+player.getX()+" y: "+player.getY(),10,Gdx.graphics.getHeight()-20);
		}
		/*for each entry in the hash map, get the value, which remember
		* is a starship class, and call the function draw to draw that
		* starship.  note that starship inherits draw method from texture*/
		for(HashMap.Entry<String, Starship> entry : friendlyPlayers.entrySet()){
			entry.getValue().draw(batch);
		}
		batch.end();
		titleStage.draw();
		optionsStage.draw();
		matchMakingStage.draw();
		dealStage.draw();
		tridentBuildingStage.draw();
		gameStage.draw();
		gameOverStage.draw();
	}
	@Override
	public void dispose () {
		batch.dispose();
		playerShip.dispose();
		friendlyShip.dispose();
	}
	public void connectSocket() {
		try {
			socket = IO.socket("http://192.168.1.6:8080");
			socket.connect();
			System.out.println("connected");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void configSocketEvents() {
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			/*when something connects to the server*/
			@Override
			public void call(Object... args) {
				Gdx.app.log("SocketIO", "Connected");
				player = new Starship(playerShip);
			}
		}).on("socketID", new Emitter.Listener() {
			/*the event listener will listen for an event called "socketID"*/
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					id = data.getString("id");
					Gdx.app.log("SocketIO", "my ID: " + id);
				} catch(JSONException e){
					Gdx.app.log("SocketIO","Error getting ID" +e);
				}
			}
		}).on("newPlayer", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String playerId = data.getString("id");
					Gdx.app.log("SocketIO", "New Player Connect: " + id);
					/*put the new player's id and the starship class in our hashmap*/
					friendlyPlayers.put(playerId, new Starship(friendlyShip));
				}catch(JSONException e){
					Gdx.app.log("SocketIO", "Error getting New PlayerID");
				}
			}
		}).on("playerDisconnected", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					/*ge thte id of the disconnected player*/
					id = data.getString("id");
					/*remove the player from the hashmap*/
					friendlyPlayers.remove(id);
				}catch(JSONException e){
					Gdx.app.log("SocketIO", "Error getting disconnected PlayerID");
				}
			}
		}).on("playerMoved", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				/*if a player moves the player will call the updateServer
				* method as defined above
				* the server will be informed of that player's position
				* and will emit a playerMoved event
				*
				* all players will listen for a playerMoved event from
				* the server and will update the position of other
				* players on screen with that data*/
				JSONObject data = (JSONObject) args[0];
				try {
					String playerId = data.getString("id");
					Double x= data.getDouble("x");
					Double y= data.getDouble("y");
					if(friendlyPlayers.get(playerId) != null){
						friendlyPlayers.get(playerId).setPosition(x.floatValue(),y.floatValue());
					}
				}catch(JSONException e){
				}
			}
		}).on("getPlayers", new Emitter.Listener() {
			/*getPlayers is for when the new player arrives and needs to know the
			* info about all the players that were there before it*/
			@Override
			public void call(Object... args) {
				/*get the players array from the server, which will contain the
				class called player, which has id and pos*/
				JSONArray objects = (JSONArray) args[0];
				try {
					for(int i = 0; i < objects.length(); i++){
						/*create a new starship for every object inside the players
						array we have been passed*/
						Starship coopPlayer = new Starship(friendlyShip);
						Vector2 position = new Vector2();
						/*apparently you cannot get a float value from a JSONObject, so cast it to double
						* but set position method below only takes floats*/
						position.x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
						position.y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
						coopPlayer.setPosition(position.x, position.y);
						/*add the new starship to the hash map*/
						friendlyPlayers.put(objects.getJSONObject(i).getString("id"), coopPlayer);
					}
				} catch(JSONException e){

				}
			}
		});

	}


	@Override
	public void hideAllStages() {
		titleStage.setVisible(false);
		optionsStage.setVisible(false);
		matchMakingStage.setVisible(false);
		dealStage.setVisible(false);
		tridentBuildingStage.setVisible(false);
		gameStage.setVisible(false);
		gameOverStage.setVisible(false);
	}

	@Override
	public void goToStage(int stage) {
		hideAllStages();
		switch (stage) {
			case TITLESTAGE :{
				titleStage.setVisible(true);
				break;
			}
			case OPTIONSSTAGE :{
				optionsStage.setVisible(true);
				break;
			}
			case MATCHMAKINGSTAGE :{
				matchMakingStage.setVisible(true);
				break;
			}
			case DEALSTAGE :{
				dealStage.setVisible(true);
				break;
			}
			case TRIDENTBUILDINGSTAGE :{
				tridentBuildingStage.setVisible(true);
				break;
			}
			case GAMESTAGE :{
				gameStage.setVisible(true);
				break;
			}
			case GAMEOVERSTAGE :{
				gameOverStage.setVisible(true);
				break;
			}
		}
	}

	/**	this method is called in any of the game stages to add a new TriButton to the stage
	 * and also keep track of it in that stage's array of triButtons
	 *
	 * @param triButton the new button
	 * @param array the array to add it to
	 * @param stage the current stage so we can add the button as an actor
	 * @param index this will be the index position of the new button in the array
	 * @return
	 */
	@Override
	public int addTriButton(TriButton triButton, Array array, Stage stage,int index) {
		array.add(triButton);
		stage.addActor(triButton);
		index++;
		return index;
	}

	/**used in game stages to get a triButton from the stage's triButtonArray
	 *
	 * @param array the Stage's triButtonArray
	 * @param index the variable that holds the associated triButton's index in the array
	 * @return
	 */
	@Override
	public TriButton getTriButton(Array array, int index) {
		/*array hold type object, so we need to cast (TriButton) else incompatible types error*/
		return (TriButton) array.get(index);
	}

	@Override
	public int addCardButton(CardButton cardButton, Array array, Stage stage, int index) {
		array.add(cardButton);
		stage.addActor(cardButton);
		index++;
		return index;
	}

	@Override
	public CardButton getCardButton(Array array, int index) {
		return (CardButton) array.get(index);
	}


}