#pragma once

#include <Component/SimpleScene.h>
#include <string>
#include <vector>
#include <Core/Engine.h>

class Tema1 : public SimpleScene {
	public:
		Tema1();
		~Tema1();

		void Init() override;

	private:
		void FrameStart() override;
		void Update(float deltaTimeSeconds) override;
		void FrameEnd() override;

		void OnInputUpdate(float deltaTime, int mods) override;
		void OnMouseMove(int mouseX, int mouseY, int deltaX, int deltaY) override;
		void OnMouseBtnPress(int mouseX, int mouseY, int button, int mods) override;

		bool hitYBrick(int i, int j);
		bool hitXBrick(int i, int j);

	protected:
		glm::mat3 modelMatrix, modelMatrixBrick, modelMatrixPowerup;
		float translateX, translateY, translatePU[50];
		float mouseXPos, radius, oldX, newX;

		int powerup;

		int timeResize;

		float upWallLen, upWallWid, sideWallLen, sideWallWid, platformLen, platformWid, brickLen, brickWid, square;
		bool launchBall;
		std::vector<Mesh*> lives;
		std::vector<Mesh*> bricks;
		std::vector<Mesh*> powerups;
};