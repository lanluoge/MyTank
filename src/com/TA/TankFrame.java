package com.TA;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class TankFrame extends Frame {
	public static final TankFrame INSTANCE=new TankFrame();
	public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
	Random r=new Random();
	Tank tank=new Tank(r.nextInt(GAME_WIDTH),r.nextInt(GAME_HEIGHT),Dir.DOWN,Group.GOOD,this);
	List<Bullet> bullets=new ArrayList<>();
	Explode explode=new Explode(200,300,this);
	Map<UUID,Tank> tanks=new HashMap<>();
	List<Explode> explodes=new ArrayList<>();
	private TankFrame() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setResizable(false);
		setTitle("tank war");
		this.addKeyListener(new MyKeyListener());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
	}
	public void addTank(Tank t){
		this.tanks.put(t.getId(),t);
	}


	public Tank getTank(UUID uuid) {
		return tanks.get(uuid);
	}
	@Override
	public void paint(Graphics g) {
		Color color=g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("子弹数量"+bullets.size(),50, 100);
		g.drawString("坦克数量"+tanks.size(), 50, 120);
		g.drawString("爆炸数量"+explodes.size(), 50, 140);
		g.setColor(color);
		tank.paint(g);
		for(int i=0;i<bullets.size();i++){
			bullets.get(i).paint(g);
		}

		tanks.values().stream().forEach((e)->{e.paint(g);});
		for(int i=0;i<bullets.size();i++){
			for(int j=0;j<tanks.size();j++){
				bullets.get(i).colldeWith(tanks.get(j));
			}
		}
		for (int i = 0; i < explodes.size(); i++) {
			explodes.get(i).paint(g);
		}
	}

	Image offImage=null;
	@Override
	public void update(Graphics g) {
		if (offImage==null) {
			offImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offImage.getGraphics();
		Color color=gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(color);
		paint(gOffScreen);
		g.drawImage(offImage, 0, 0, null);
	}

	public Tank getMainTank() {
		return this.tank;
	}



	class MyKeyListener extends KeyAdapter {

		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;

			default:
				break;
			}
			seMovDir();
		}

		private void seMovDir() {
			if(!bL&&!bD&&!bR&&!bU) tank.setMoving(false);
			else {
				tank.setMoving(true);
				if (bD) tank.setDir(Dir.DOWN);
				if (bL) tank.setDir(Dir.LEFT);
				if (bR) tank.setDir(Dir.RIGHT);
				if (bU) tank.setDir(Dir.UP);
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {

			int key = e.getKeyCode();
			switch (key) {
				case KeyEvent.VK_LEFT:
					bL = false;
					break;
				case KeyEvent.VK_UP:
					bU = false;
					break;
				case KeyEvent.VK_RIGHT:
					bR = false;
					break;
				case KeyEvent.VK_DOWN:
					bD = false;
					break;
				case KeyEvent.VK_CONTROL:
					tank.fire();
					break;
				default:
					break;
			}

			seMovDir();
		}

	}

}
