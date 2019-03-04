#pragma once

#include <Component/SimpleScene.h>
#include <Component/Transform/Transform.h>
#include <Core/GPU/Mesh.h>
#include "Camera.h"

#include "Ball.h"
#include "Manta.h"
#include "Tac.h"
#include "Transform3D.h"

class Tema2 : public SimpleScene {
public:
	Tema2();
	~Tema2();

	void Init() override;

private:
	void FrameStart() override;
	void Update(float deltaTimeSeconds) override;
	void FrameEnd() override;

	void RenderSimpleMesh(Mesh *mesh, Shader *shader, const glm::mat4 &modelMatrix, const glm::vec3 &color = glm::vec3(1));
	void RenderMesh3D(Mesh * mesh, Shader * shader, const glm::mat4 & modelMatrix, const glm::vec3 &color = glm::vec3(1));

	void OnInputUpdate(float deltaTime, int mods) override;
	void OnMouseMove(int mouseX, int mouseY, int deltaX, int deltaY) override;
	void OnMouseBtnRelease(int mouseX, int mouseY, int button, int mods) override;

	void initialize();

	glm::mat4 modelMatrix;
	Lab::Camera *camera;
	glm::mat4 projectionMatrix;

	Mesh *tableM, *ballM;

	Manta *table, *mantaBack, *mantaFront, *mantaLeft, *mantaRight;
	Ball *whiteBall, *blackBall;
	Tac *tac;
	vector<Ball*> redBalls;
	vector<Ball*> yellowBalls;
	vector<Ball*> pockets;
	bool setWhiteBall, hitBall;
	float angularStepOY, coeficient;
};