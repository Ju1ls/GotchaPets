package cz.jull.gamestates.pet_list;

import cz.jull.Game;
import cz.jull.gamestates.Button;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetSpecies;
import cz.jull.ui.PetListUI;
import cz.jull.ui.UtilsUI;
import cz.jull.utils.SaveManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PetListState extends State implements StateMethods {
    // Buttons
    private Button backButton;
    private Button[] buttons;

    private final PetSpecies[] slotSpecies = {
            PetSpecies.CAT, PetSpecies.DOG, PetSpecies.RABBIT, PetSpecies.HAMSTER, PetSpecies.PARROT,
            PetSpecies.OCTOPUS, PetSpecies.SHARK, PetSpecies.TURTLE, PetSpecies.SEAL, PetSpecies.JELLYFISH
    };

    // Pet List
    private PetListUI petListUI;

    public PetListState(Game game) {
        super(game);

        petListUI = new PetListUI();
        loadButtons();
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            if (button != null) {
                button.update();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        petListUI.draw(g, game.getPlayer().getInventory());

        for (Button button : buttons) {
            if (button != null) {
                button.draw(g);
            }
        }
    }

    private void loadButtons() {
        backButton = UtilsUI.createBackButton(20, 20);
        buttons = new Button[] {backButton};
    }

    private Rectangle getPetHitbox(int index) {
        int cols = 5;

        int xOffsetStart = 50;
        int yOffsetStart = 215;
        int xSpacing = 100 + 220;
        int ySpacing = 110 + 235;
        int hitboxWidth = 220;
        int hitboxHeight = 235;

        int row = index / cols;
        int col = index % cols;

        int xCorrection = 0;
        if (col == 3 || col == 4) {
            xCorrection = -5;
        }

        int x = xOffsetStart + (col * xSpacing) + xCorrection;
        int y = yOffsetStart + (row * ySpacing);

        return new Rectangle(x, y, hitboxWidth, hitboxHeight);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {

            if (e.getClickCount() == 2) {
                for (int i = 0; i < 10; i++) {
                    Rectangle hitbox = getPetHitbox(i);

                    if (hitbox.contains(e.getX(), e.getY())) {
                        PetSpecies clickedSpecies = slotSpecies[i];

                        for (Pet ownedPet : game.getPlayer().getInventory()) {
                            if (ownedPet.getSpecies() == clickedSpecies) {
                                game.getPlayer().setEquippedPet(ownedPet);
                                SaveManager.saveGame(game.getPlayer());
                                game.returnToPlaying();
                                return;
                            }
                        }
                        return;
                    }
                }
            }

            for (Button button : buttons) {
                if (button != null && button.isMouseOver()) {
                    button.setMousePressed(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (backButton.isMouseOver() && backButton.isMousePressed()) {
                game.returnToPlaying();
            }

            for (Button button : buttons) {
                if (button != null) {
                    button.setMousePressed(false);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (Button button : buttons) {
            if (button != null) {
                button.setMouseOver(false);
            }
        }

        for (Button button : buttons) {
            if (button != null && button.getBounds().contains(e.getX(), e.getY())) {
                button.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void KeyPressed(KeyEvent e) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }
}
