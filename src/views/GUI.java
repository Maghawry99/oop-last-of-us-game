package views;

import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.awt.TextField;
import java.lang.reflect.Array;

import org.junit.validator.PublicClassValidator;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
//import javafx.scene.paint.color;

import javafx.stage.Stage;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class GUI extends Application {
	private Hero selectedHero;
	private Button[][] Buttonarray = new Button[15][15];
//	private Text  errorText;
	

	public static void main(String[] args) {
		launch(args);

	}

	public void start(Stage primaryStage) throws Exception {
		Image imageIconImage = new Image(
				"views/The Last Of Us Live Wallpaper › Live Wallpaper for PC & Phone - Find Best Live Wallpapers_.jpg");
		ImageView viewImage = new ImageView(imageIconImage);
		BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
		BackgroundImage backgroundim = new BackgroundImage(imageIconImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
		Background background = new Background(backgroundim);

		Image imageIconImage2 = new Image("views/startbut.png");
		ImageView viewImage2 = new ImageView(imageIconImage2);
		BackgroundSize size2 = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
		BackgroundImage backgroundim2 = new BackgroundImage(imageIconImage2, BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size2);
		Background background2 = new Background(backgroundim2);

		Image imageIconImage3 = new Image("views/exit.png");
		ImageView viewImage3 = new ImageView(imageIconImage3);
		BackgroundSize size3 = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
		BackgroundImage backgroundim3 = new BackgroundImage(imageIconImage3, BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size3);
		Background background3 = new Background(backgroundim3);
		
		Text  errorText = new Text();
		errorText.setTranslateX(0);
		errorText.setTranslateY(300);

		try {
			Game.loadHeroes("src\\views\\Heroes.csv");
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("No Heroes Available");
			alert.show();

		}
		Pane stackpane3 = new Pane();
		Scene Grid = new Scene(stackpane3);
		GridPane mapPane = new GridPane();
		mapPane.setMinSize(500, 500);
		mapPane.setTranslateX(600);
		mapPane.setTranslateY(100);
		
		
		VBox herosInfo = new VBox(10);
		herosInfo.setTranslateX(50);
		herosInfo.setTranslateY(50);
		
		
		Button attButton = new Button();
		attButton.setMinSize(70, 70);
		attButton.setTranslateX(300);
		attButton.setTranslateY(350);
		attButton.setBackground(back("views/swords.png"));
		attButton.setOnAction(event -> {
			try {
				
				selectedHero.attack();
				createMap(mapPane);
				updateHeroInfo(herosInfo);
				if(Game.checkGameOver()) {
					Button exit = new Button("YOU LOSE");
					exit.setMinSize(1000,1000);
//					
//					exit.setTranslateX(500);
//					exit.setTranslateY(500);
					exit.setOnAction(t -> primaryStage.close());
					stackpane3.getChildren().add(exit);
				}
			} catch (Exception e) {
				
				errorText.setText(e.getMessage());
			}

		});
		Button moveUp = new Button();
		moveUp.setMinSize(70, 70);
		moveUp.setBackground(back("views/arrow-up.png"));
		moveUp.setOnAction(e -> {
			try {
				selectedHero.move(Direction.DOWN);
				createMap(mapPane );
				updateHeroInfo(herosInfo);
				if(Game.checkGameOver()) {
					Button exit = new Button("YOU LOSE");
					exit.setMinSize(1000,1000);
//					
//					exit.setTranslateX(500);
//					exit.setTranslateY(500);
					exit.setOnAction(t -> primaryStage.close());
					stackpane3.getChildren().add(exit);
				}

			} catch (MovementException e1) {
				// TODO Auto-generated catch block
				errorText.setText(e1.getMessage());
				e1.printStackTrace();
			} catch (NotEnoughActionsException e1) {
				// TODO Auto-generated catch block
				errorText.setText(e1.getMessage());
				e1.printStackTrace();
			}
		});

		Button cure = new Button();
		cure.setMinSize(70, 70);
		cure.setTranslateX(300);
		cure.setTranslateY(200);
		cure.setBackground(back("views/syringe.png"));
		cure.setOnAction(event -> {
			try {
				
				
				selectedHero.cure();
				createMap(mapPane);
				updateHeroInfo(herosInfo);
				
				if(Game.checkWin()) {
					Button exit = new Button("YOU WIN");
					exit.setMinSize(1000,1000);
					
//					exit.setTranslateX(500);
//					exit.setTranslateY(500);
					exit.setOnAction(t -> primaryStage.close());
					stackpane3.getChildren().add(exit);
				
				
					
				}
			} catch (Exception e) {
				
				errorText.setText(e.getMessage());
			}

		});

		Button moveDown = new Button();
		moveDown.setMinSize(70, 70);
		moveDown.setBackground(back("views/arrow-pointing-down.png"));
		moveDown.setOnAction(e -> {
			try {
				selectedHero.move(Direction.UP);
				createMap(mapPane);
				updateHeroInfo(herosInfo);
				if(Game.checkGameOver()) {
					Button exit = new Button("YOU LOSE");
					exit.setMinSize(1000,1000);
					
//					exit.setTranslateX(500);
//					exit.setTranslateY(500);
					exit.setOnAction(t -> primaryStage.close());
					stackpane3.getChildren().add(exit);
				}

			} catch (MovementException e1) {
				// TODO Auto-generated catch block
				errorText.setText(e1.getMessage());
				e1.printStackTrace();
			} catch (NotEnoughActionsException e1) {
				// TODO Auto-generated catch block
				errorText.setText(e1.getMessage());
				e1.printStackTrace();
			}
		});
		
		
		// Up DOwn
		VBox moveBox = new VBox(30);
		moveBox.getChildren().addAll(moveUp, moveDown);
		moveBox.setTranslateX(300);
		moveBox.setTranslateY(500);

		HBox move1box = new HBox(30);

		// Right Left
		Button moveRight = new Button();
		moveRight.setMinSize(70, 70);
		moveRight.setBackground(back("views/arrow-pointing-to-right.png"));
		moveRight.setOnAction(e -> {
			try {
				selectedHero.move(Direction.RIGHT);
				createMap(mapPane);
				updateHeroInfo(herosInfo);
				if(Game.checkGameOver()) {
					Button exit = new Button("YOU LOSE");
					exit.setMinSize(1000,1000);
					
//					exit.setTranslateX(500);
//					exit.setTranslateY(500);
					exit.setOnAction(t -> primaryStage.close());
					stackpane3.getChildren().add(exit);
				}

			} catch (MovementException e1) {
				// TODO Auto-generated catch block
				errorText.setText(e1.getMessage());
				e1.printStackTrace();
			} catch (NotEnoughActionsException e1) {
				// TODO Auto-generated catch block
				errorText.setText(e1.getMessage());
				e1.printStackTrace();
			}
		});
		
		
		Button moveleft = new Button();
		moveleft.setMinSize(70, 70);
		moveleft.setBackground(back("views/arrow-pointing-to-left.png"));
		moveleft.setOnAction(e -> {
			try {
				selectedHero.move(Direction.LEFT);
				createMap(mapPane);
				updateHeroInfo(herosInfo);
				if(Game.checkGameOver()) {
					Button exit = new Button("YOU LOSE");
					exit.setMinSize(1000,1000);
//					
//					exit.setTranslateX(500);
//					exit.setTranslateY(500);
					exit.setOnAction(t -> primaryStage.close());
					stackpane3.getChildren().add(exit);
				}

			} catch (MovementException e1) {
				// TODO Auto-generated catch block
				errorText.setText(e1.getMessage());
				e1.printStackTrace();
			} catch (NotEnoughActionsException e1) {
				// TODO Auto-generated catch block
				errorText.setText(e1.getMessage());
				e1.printStackTrace();
			}
		});
		
		
		Button endturnButton = new Button("End Turn");
		endturnButton.setTranslateX(300);
		endturnButton.setTranslateY(100);
		endturnButton.setMinSize(70, 70);
		endturnButton.setOnAction(e -> {
			try {
				Game.endTurn();
				createMap(mapPane);
				updateHeroInfo(herosInfo);
				if(Game.checkGameOver()) {
					Button exit = new Button("YOU LOSE");
					exit.setMinSize(1000,1000);
					
				//	exit.setTranslateX(0);
					//exit.setTranslateY(0);
					exit.setOnAction(t -> primaryStage.close());
					stackpane3.getChildren().add(exit);
				}
			} catch (InvalidTargetException ev) {
				// TODO: handle exception
				errorText.setText(ev.getMessage());
			} catch (NotEnoughActionsException ev2) {
				errorText.setText(ev2.getMessage());
				// TODO: handle exception
			}
		});
		
		Button useSpecialaction = new Button("Use Special Action");
		useSpecialaction.setTranslateX(400);
		useSpecialaction.setTranslateY(100);
		useSpecialaction.setMinSize(70, 70);
		useSpecialaction.setOnAction(event -> {
			try {
				
				selectedHero.useSpecial();
				createMap(mapPane);
				updateHeroInfo(herosInfo);
			} catch (Exception e) {
				
				errorText.setText(e.getMessage());
			}

		});
		
		
		

		move1box.getChildren().addAll(moveleft, moveRight);
		move1box.setTranslateX(250);
		move1box.setTranslateY(550);
		
		

		Pane stackPane2 = new Pane();
		Scene hero1 = new Scene(stackPane2, 1000, 800);
		stackPane2.setBackground(back(
				"views/The Last Of Us Live Wallpaper › Live Wallpaper for PC & Phone - Find Best Live Wallpapers_.jpg"));

		

		Button joelMillerButton = new Button();
		joelMillerButton.setMinSize(100, 100);
		joelMillerButton.setBackground(back("views/Joel part 1 remake.jpg"));
		joelMillerButton.setOnAction(event -> {
			primaryStage.setScene(Grid);
			primaryStage.setFullScreen(true);
			Hero hero = Game.availableHeroes.get(0);
			selectedHero = hero;
			Game.startGame(selectedHero);
			createMap(mapPane);
			updateHeroInfo(herosInfo);
		});

		// Select Champ
		Text selecText = new Text("Select Your Hero:");
		selecText.setFont(new Font(STYLESHEET_CASPIAN, 70));
		selecText.setTranslateX(150);
		selecText.setTranslateY(150);
		selecText.setFill(javafx.scene.paint.Color.WHITE);
		stackPane2.getChildren().add(selecText);

		Button ellieWilliamButton = new Button();
		ellieWilliamButton.setMinSize(100, 100);
		ellieWilliamButton.setBackground(back("views\\ellie williams.jpg"));
		ellieWilliamButton.setOnAction(event -> {
			primaryStage.setScene(Grid);
			primaryStage.setFullScreen(true);
			Hero hero = Game.availableHeroes.get(1);
			selectedHero = hero;
			Game.startGame(selectedHero);
			createMap(mapPane);
			updateHeroInfo(herosInfo);
		});

		Button tessButton = new Button();
		tessButton.setMinSize(100, 100);
		tessButton.setBackground(back("views/Theresa Servopoulos.jpg"));
		tessButton.setOnAction(event -> {
			primaryStage.setScene(Grid);
			primaryStage.setFullScreen(true);
			Hero hero = Game.availableHeroes.get(2);
			selectedHero = hero;
			Game.startGame(selectedHero);
			createMap(mapPane);
			updateHeroInfo(herosInfo);
		});

		Button rileyAbelButton = new Button();
		rileyAbelButton.setBackground(back("views/Part 1 remake left behind.jpg"));
		rileyAbelButton.setMinSize(100, 100);
		rileyAbelButton.setOnAction(event -> {
			primaryStage.setScene(Grid);
			primaryStage.setFullScreen(true);
			Hero hero = Game.availableHeroes.get(3);
			selectedHero = hero;
			Game.startGame(selectedHero);
			createMap(mapPane);
			updateHeroInfo(herosInfo);
		});

		Button tommyButton = new Button();
		tommyButton.setBackground(back("views/TOMMY.jpg"));
		tommyButton.setMinSize(100, 100);
		tommyButton.setOnAction(event -> {
			primaryStage.setScene(Grid);
			primaryStage.setFullScreen(true);
			Hero hero = Game.availableHeroes.get(4);
			selectedHero = hero;
			Game.startGame(selectedHero);
			createMap(mapPane);
			updateHeroInfo(herosInfo);
		});

		Button billButton = new Button();
		billButton.setBackground(back("views/bill.jpg"));
		billButton.setMinSize(100, 100);
		billButton.setOnAction(event -> {
			primaryStage.setScene(Grid);
			primaryStage.setFullScreen(true);
			Hero hero = Game.availableHeroes.get(5);
			selectedHero = hero;
			Game.startGame(selectedHero);
			createMap(mapPane);
			updateHeroInfo(herosInfo);
		});

		Button davidButton = new Button();
		davidButton.setBackground(back("views/David.png"));
		davidButton.setMinSize(100, 100);
		davidButton.setOnAction(event -> {
			primaryStage.setScene(Grid);
			primaryStage.setFullScreen(true);
			Hero hero = Game.availableHeroes.get(6);
			selectedHero = hero;
			Game.startGame(selectedHero);
			createMap(mapPane);
			updateHeroInfo(herosInfo);
		});

		Button henryButton = new Button();
		henryButton.setBackground(back("views/Henry.jpg"));
		henryButton.setMinSize(100, 100);
		henryButton.setOnAction(event -> {
			primaryStage.setScene(Grid);
			primaryStage.setFullScreen(true);
			Hero hero = Game.availableHeroes.get(7);
			selectedHero = hero;
			Game.startGame(selectedHero);
			createMap(mapPane);
			updateHeroInfo(herosInfo);
		});
		// Heroes Info
		Text jmText = new Text(HeroesInfo(Game.availableHeroes.get(0)));
		jmText.setFill(javafx.scene.paint.Color.WHITE);

		Text ellieWillText = new Text(HeroesInfo(Game.availableHeroes.get(1)));
		ellieWillText.setFill(javafx.scene.paint.Color.WHITE);

		Text tessText = new Text(HeroesInfo(Game.availableHeroes.get(2)));
		tessText.setFill(javafx.scene.paint.Color.WHITE);

		Text rileyText = new Text(HeroesInfo(Game.availableHeroes.get(3)));
		rileyText.setFill(javafx.scene.paint.Color.WHITE);

		Text tommyText = new Text(HeroesInfo(Game.availableHeroes.get(4)));
		tommyText.setFill(javafx.scene.paint.Color.WHITE);

		Text billText = new Text(HeroesInfo(Game.availableHeroes.get(5)));
		billText.setFill(javafx.scene.paint.Color.WHITE);

		Text davidText = new Text(HeroesInfo(Game.availableHeroes.get(6)));
		davidText.setFill(javafx.scene.paint.Color.WHITE);

		Text henryText = new Text(HeroesInfo(Game.availableHeroes.get(7)));
		henryText.setFill(javafx.scene.paint.Color.WHITE);

		HBox hereosBox1 = new HBox();
		hereosBox1.getChildren().addAll(joelMillerButton, jmText, ellieWilliamButton, ellieWillText, tessButton,
				tessText, rileyAbelButton, rileyText);
		hereosBox1.setTranslateX(300);
		hereosBox1.setTranslateY(200);

		HBox hereosBox2 = new HBox();
		hereosBox2.getChildren().addAll(tommyButton, tommyText, billButton, billText, davidButton, davidText,
				henryButton, henryText);
		hereosBox2.setTranslateX(300);
		hereosBox2.setTranslateY(500);

		stackPane2.getChildren().addAll(hereosBox1, hereosBox2);
		
		
		stackpane3.getChildren().addAll(mapPane, moveBox, move1box, attButton, cure, endturnButton,useSpecialaction,herosInfo,errorText);
		
		// HomePage
		StackPane stackPane = new StackPane();
		stackPane.setBackground(background);
		Button startButton = new Button();
		startButton.setMinSize(140, 50);
		startButton.setBackground(background2);
		startButton.setTranslateX(-400);
		startButton.setTranslateY(250);
		startButton.setOnAction(e -> {
			primaryStage.setScene(hero1);
			primaryStage.setFullScreen(true);
			;
		});

		Button exit = new Button();
		exit.setMinSize(140, 85);
		exit.setBackground(background3);
		exit.setTranslateX(400);
		exit.setTranslateY(250);
		exit.setOnAction(e -> primaryStage.close());

		stackPane.getChildren().addAll(startButton, exit);

		Scene scene = new Scene(stackPane, 1000, 1000);

		// end of HomePage

		primaryStage.setScene(scene);
		//primaryStage.setScene(scene);
		Image toplefImage = new Image("views/Last Of us.png");
		primaryStage.getIcons().add(toplefImage);
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("The Last Of Us");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void createMap(GridPane mapPane) {
		mapPane.getChildren().clear();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Button xButton = new Button();
				xButton.setMinHeight(50);
				xButton.setMinWidth(50);
				if (Game.map[i][j] instanceof TrapCell) {
					xButton.setBackground(back("A_black_image.jpg"));
				}
				if (Game.map[i][j] instanceof CollectibleCell) {
					if (((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Vaccine) {
						xButton.setBackground(back("syringe.png"));
					} else {
						xButton.setBackground(back("4994463.png"));
					}
				}
				if (Game.map[i][j] instanceof CharacterCell) {
					if (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Zombie) {
						xButton.setBackground(back("12-zombie-icons-4.png"));
					}
					if (((CharacterCell) Game.map[i][j]).getCharacter() == null) {
						xButton.setBackground(back("A_black_image.jpg"));
					}

					if (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Hero) {
						CharacterCell cell = (CharacterCell) Game.map[i][j];
						xButton.setBackground(back(GetImage((Hero) cell.getCharacter())));
					}

				}
				xButton.setOpacity(0);
				if((Game.map[i][j].isVisible())) {
					xButton.setOpacity(1);
				}
				mapPane.add(xButton, j, i);
				int x = i;
				int y = j;
				xButton.setOnAction(e-> {
				if (Game.map[x][y] instanceof CharacterCell) {
					if (((CharacterCell) Game.map[x][y]).getCharacter() != null) {
						selectedHero.setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
						
						if(!selectedHero.checkDistance()) {
							
							selectedHero.setTarget(null);
						}
					
					}
				}
			
			});
				 xButton.setOnMouseClicked(event -> {
			            if (event.getClickCount() == 2) {
			            	if (Game.map[x][y] instanceof CharacterCell) {
			            		CharacterCell cell =(CharacterCell) Game.map[x][y];
								if ( cell.getCharacter() instanceof Hero) {
									selectedHero = (Hero) cell.getCharacter();
								}
			            	}
			            }
			        });
			}}
		
	}

	public static Background back(String s) {
		Image imageIconImage = new Image(s);
		BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
		BackgroundImage backgroundim = new BackgroundImage(imageIconImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
		Background background = new Background(backgroundim);
		return background;
	}

	public String HeroesInfo(Hero h) {
		String string = "";
		String t = "";

		if (h instanceof Medic) {
			t = "Medic";
		} else if (h instanceof Explorer) {
			t = "Explorer";

		} else if (h instanceof Fighter) {
			t = "Fighter";

		}
		string = "Name: " + h.getName() + "\n" + "Type: " + t + "\n" + "Hp: " + h.getMaxHp() + "\n" + "MaxActions: "
				+ h.getMaxActions() + "\n" + "AttackDamage: " + h.getAttackDmg();

		return string;
	}
	
	public String HeroesInfo2(Hero h) {
		String string = "";
		String t = "";

		if (h instanceof Medic) {
			t = "Medic";
		} else if (h instanceof Explorer) {
			t = "Explorer";

		} else if (h instanceof Fighter) {
			t = "Fighter";

		}
		string = "Name: " + h.getName() + "\n" + "Type: " + t + "\n" + "Hp: " + h.getCurrentHp() + "\n" + "Actions: "
				+ h.getActionsAvailable() + "\n" + "AttackDamage: " + h.getAttackDmg()+ "\nSupplies: " + h.getSupplyInventory().size()
				+ "\nVaccines" + h.getVaccineInventory().size();

		return string;
	}
	
	public void updateHeroInfo(VBox v) {
		v.getChildren().clear();
		for(int i=0;i<Game.heroes.size();i++) {
			Text text = new Text(HeroesInfo2(Game.heroes.get(i)));
			v.getChildren().add(text);
		}
	}

	public int GetLocationX(Button b) {
		int x = 0;
		for (int i = 0; i < Buttonarray.length; i++) {
			for (int j = 0; j < Buttonarray.length; j++) {
				if (Buttonarray[i][j].equals(b)) {
					x = i;
				}

			}

		}
		return x;
	}
	public String GetImage(Hero h) {
		String string = "";
		if(h.getName().equals("Joel Miller")) {
			string = "views/Joel part 1 remake.jpg";
			
		}
		if(h.getName().equals("Ellie Williams")) {
			string = "views/ellie williams.jpg";
			
		}
		if(h.getName().equals("Tess")) {
			string = "views/Theresa Servopoulos.jpg";
			
		}
		if(h.getName().equals("Riley Abel")) {
			string = "views/Part 1 remake left behind.jpg";
			
		}
		if(h.getName().equals("Tommy Miller")) {
			string = "views/TOMMY.jpg";
			
			
		}
		if(h.getName().equals("Bill")) {
			string = "views/bill.jpg";
			
		}
		if(h.getName().equals("David")) {
			string = "views/David.png";
			
		}
		if(h.getName().equals("Henry Burrel")) {
			string = "views/Henry.jpg";
			
		}
		return string;
	}

}
