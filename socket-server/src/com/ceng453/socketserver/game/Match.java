package com.ceng453.socketserver.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Match implements Runnable {

    private DataInputStream in;
    private DataOutputStream out;
    private Gamer gamer1;
    private Gamer gamer2;
    private boolean gameOn = true;
    private Double[] gamer1Coords;
    private Double[] gamer2Coords;
    private Double alienX = 300.0;
    private Double alienY = 150.0;
    private Double[] alienCoords;
    private int alienHealth = 15;

    public Match(Gamer gamer1, Gamer gamer2){
        this.gamer1 = gamer1;
        this.gamer2 = gamer2;
    }

    @Override
    public void run() {
        gamer1Coords = new Double[2];
        gamer2Coords = new Double[2];
        alienCoords = new Double[2];
        alienCoords[0] = alienX;
        alienCoords[1] = alienY;
        try {
            sendUsername(gamer1, gamer2);
            sendUsername(gamer2, gamer1);
            sendHealth(gamer1, gamer2);
            sendHealth(gamer2, gamer1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (gameOn){
            try {

                boolean gameEnd1 = getGameStatus(gamer1);
                boolean gameEnd2 = getGameStatus(gamer2);
                if(gameEnd1 || gameEnd2){
                    gameOn = false;
                    sendGameStatus(gamer1, true);
                    sendGameStatus(gamer2, true);
                    break;
                }
                sendGameStatus(gamer1, false);
                sendGameStatus(gamer2, false);

                // send each other their coordinate data
                sendData(gamer1, gamer2, gamer1Coords);
                sendData(gamer2, gamer1, gamer2Coords);
                // send random alien coordinates
                sendAlienCoords(gamer1, gamer2);

                // randomly make alien shoot
                if(Math.random() < 0.01){
                    sendCanAlienShoot(gamer1, true);
                    sendCanAlienShoot(gamer2, true);
                }
                else {
                    sendCanAlienShoot(gamer1, false);
                    sendCanAlienShoot(gamer2, false);
                }
                //update players health
                sendHealth(gamer1, gamer2);
                sendHealth(gamer2, gamer1);

                gameEnd1 = getGameStatus(gamer1);
                gameEnd2 = getGameStatus(gamer2);
                if(gameEnd1 || gameEnd2){
                    gameOn = false;
                    sendGameStatus(gamer1, true);
                    sendGameStatus(gamer2, true);
                    break;
                }
                sendGameStatus(gamer1, false);
                sendGameStatus(gamer2, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            int score1 = getScores(gamer1);
            int score2 = getScores(gamer2);
            sendScores(gamer1, score2);
            sendScores(gamer2, score1);
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends coordinates of players
     * @param gamer1 gamer1 sends
     * @param gamer2 gamer2 takes
     * @param gamer1Coords gamer1's coordinates
     * @throws IOException IOException
     */
    private void sendData(Gamer gamer1, Gamer gamer2, Double[] gamer1Coords) throws IOException {
        in = new DataInputStream(gamer1.getSocket().getInputStream());
        for(int i=0; i<2; i++){
            gamer1Coords[i] = in.readDouble();
        }
        out = new DataOutputStream(gamer2.getSocket().getOutputStream());
        for(int i=0; i<2; i++){
            out.writeDouble(gamer1Coords[i]);
        }
    }

    /**
     * get scores of players
     * @param gamer to get score
     * @return score of player
     * @throws IOException IOException
     */
    private int getScores(Gamer gamer) throws IOException {
        in = new DataInputStream(gamer.getSocket().getInputStream());
        int score = in.readInt();
        return score;
    }

    /**
     * send score
     * @param gamer to send score
     * @param score score
     * @throws IOException IOException
     */
    private void sendScores(Gamer gamer, int score) throws IOException {
        out = new DataOutputStream(gamer.getSocket().getOutputStream());
        out.writeInt(score);
    }

    /**
     * move alien
     * @param gamer send to who
     * @return should alien move
     * @throws IOException IOException
     */
    private boolean moveAlien(Gamer gamer) throws IOException {
        in = new DataInputStream(gamer.getSocket().getInputStream());
        boolean status = in.readBoolean();
        return status;
    }

    /**
     * send username
     * @param gamer1 gamer1
     * @param gamer2 gamer2
     * @throws IOException IOException
     */
    private void sendUsername(Gamer gamer1, Gamer gamer2) throws IOException {
        in = new DataInputStream(gamer1.getSocket().getInputStream());
        String gamer1Username = in.readUTF();
        out = new DataOutputStream(gamer2.getSocket().getOutputStream());
        out.writeUTF(gamer1Username);
    }

    /**
     * send game status
     * @param gamer to send
     * @param status status to send
     * @throws IOException IOException
     */
    private void sendGameStatus(Gamer gamer, boolean status) throws IOException {
        out = new DataOutputStream(gamer.getSocket().getOutputStream());
        out.writeBoolean(status);
    }

    /**
     * send health
     * @param gamer1 gamer1
     * @param gamer2 gamer2
     * @throws IOException IOException
     */
    private void sendHealth(Gamer gamer1, Gamer gamer2) throws IOException {
        in = new DataInputStream(gamer1.getSocket().getInputStream());
        int gamer1Health = in.readInt();
        out = new DataOutputStream(gamer2.getSocket().getOutputStream());
        out.writeInt(gamer1Health);
    }

    /**
     * creates random alien coordinates
     */
    private void alienCoordinates() {
        Double randomX = -50 + (Math.random() * ((50 - (-50)) + 1));
        Double randomY = -50 + (Math.random() * ((50 - (-50)) + 1));
        if(50 < alienX + randomX && alienX + randomX < 650)
            alienX += randomX;
        if(50 < alienY + randomY && alienY + randomY < 300)
            alienY += randomY;
        alienCoords[0] = alienX;
        alienCoords[1] = alienY;
    }

    /**
     * sends alien coordinates
     * @param gamer1 to gamer1
     * @param gamer2 to gamer2
     * @throws IOException IOException
     */
    private void sendAlienCoords(Gamer gamer1, Gamer gamer2) throws IOException {
        boolean gamer1Move = moveAlien(gamer1);
        boolean gamer2Move = moveAlien(gamer2);
        if(gamer1Move || gamer2Move)
            alienCoordinates();
        out = new DataOutputStream(gamer1.getSocket().getOutputStream());
        for(int i=0; i<2; i++){
            out.writeDouble(alienCoords[i]);
        }
        out = new DataOutputStream(gamer2.getSocket().getOutputStream());
        for(int i=0; i<2; i++){
            out.writeDouble(alienCoords[i]);
        }
    }

    /**
     * should alien shoot
     * @param gamer gamer
     * @param status should shoot
     * @throws IOException IOException
     */
    private void sendCanAlienShoot(Gamer gamer, boolean status) throws IOException {
        out = new DataOutputStream(gamer.getSocket().getOutputStream());
        out.writeBoolean(status);
    }

    /**
     * get game status
     * @param gamer gamer
     * @return is end
     * @throws IOException IOException
     */
    private boolean getGameStatus(Gamer gamer) throws IOException {
        in = new DataInputStream(gamer.getSocket().getInputStream());
        boolean status = in.readBoolean();
        return status;
    }

}
