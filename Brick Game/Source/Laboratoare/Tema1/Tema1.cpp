#include "Tema1.h"

#include <vector>
#include <iostream>

#include <Core/Engine.h>
#include "Obiect2D.h"
#include "Transformari2D.h"

#include "Wall.h"
#include "Platform.h"
#include "Ball.h"
#include "Brick.h"

using namespace std;

bool firstAttempt = true;
bool hitPlatform = false;
bool hitCaramida = false;

int num = 3;
float tx = 1.0f, ty = 1.0f;
float sx[50], sy[50], rx[50];
bool scaleBrick[50], rotatePowerUp[50];

Wall* wall_up, *wall_left, *wall_right, *wall_down;
Platform* platform;
Ball* circle;
Brick* rectangle;
Brick* power_up;

Tema1::Tema1() {
	// dimensiunile elementelor din scena
	upWallLen = 1280.0f;
	upWallWid = 25.0f;
	sideWallLen = 25.0f;
	sideWallWid = 690.0f;
	platformLen = 200.0f;
	platformWid = 20.0f;
	brickLen = 90.0f;
	brickWid = 60.0f;
	radius = 10.0f;
	square = 15.0f;

	launchBall = false;
	
	translateY = 50.0f;
}

Tema1::~Tema1() {
}

void Tema1::Init() {
	glm::ivec2 resolution = window->GetResolution();
	auto camera = GetSceneCamera();
	camera->SetOrthographic(0, (float)resolution.x, 0, (float)resolution.y, 0.01f, 400);
	camera->SetPosition(glm::vec3(0, 0, 50));
	camera->SetRotation(glm::vec3(0, 0, 0));
	camera->Update();
	GetCameraInput()->SetActive(false);

	// crearea elementelor din scena
	wall_up = new Wall(upWallLen, upWallWid);
	wall_down = new Wall(upWallLen, upWallWid);
	wall_left = new Wall(sideWallLen, sideWallWid);
	wall_right = new Wall(sideWallLen, sideWallWid);
	platform = new Platform(platformLen, platformWid);
	circle = new Ball(radius);
	rectangle = new Brick(brickLen, brickWid);
	power_up = new Brick(square, square);

	// initializare variabile auxiliare, precum factorul de scalare, rotire, si cateva variabile boolene
	// folosite la determinarea unor actiuni
	for (int i = 0; i < 50; i++) {
		sx[i] = 1.0f;
		sy[i] = 1.0f;
		scaleBrick[i] = false;
		rx[i] = 90.0f;
		rotatePowerUp[i] = false;
		translatePU[i] = (i % 10) * 90.0f + 200.0f;
	}

	//punere elemente in scena

	// perete top
	Mesh* wallUp = wall_down->wallUp(glm::vec3(1, 0, 0), true, "wallUp");
	AddMeshToList(wallUp);

	// perete down
	Mesh* wallDown = wall_up->wallUp(glm::vec3(1, 0, 0), true, "wallDown");
	AddMeshToList(wallDown);

	// perete stanga
	Mesh* wallLeft = wall_left->wallSide(glm::vec3(1, 0, 0), true, "wallLeft");
	AddMeshToList(wallLeft);

	// perete dreapta
	Mesh* wallRight = wall_right->wallSide(glm::vec3(1, 0, 0), true, "wallRight");
	AddMeshToList(wallRight);

	// platforma
	Mesh* platforma = platform->getPlatform(glm::vec3(1, 1, 0), true, "platform");
	AddMeshToList(platforma);

	// minge
	Mesh* ball = circle->getBall(glm::vec3(1, 0, 1), true, "ball");
	AddMeshToList(ball);

	// vieti
	for (int i = 0; i < 3; i++) {
		Mesh* life = circle->getBall(glm::vec3(1, 0, 1), true, "life" + std::to_string(i));
		lives.push_back(life);
		AddMeshToList(life);
	}

	// caramizi
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 10; j++) {
			Mesh* brick = rectangle->getBrick(glm::vec3(0, 0, 1), true, "brick" + std::to_string(i) + std::to_string(j));
			bricks.push_back(brick);
			AddMeshToList(brick);
		}
	}

	// power ups -> fiecare caramida sa poata genera un power up
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 10; j++) {
			Mesh* powerUp = power_up->getBrick(glm::vec3(1, 0.4, 0.6), true, "square" + std::to_string(i) + std::to_string(j));
			powerups.push_back(powerUp);
			AddMeshToList(powerUp);
		}
	}
}

void Tema1::FrameStart() {
	glClearColor(0, 0, 0, 1);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glm::ivec2 resolution = window->GetResolution();
	glViewport(0, 0, resolution.x, resolution.y);
}

void Tema1::Update(float deltaTimeSeconds) {
	// un element random care sa determine daca distrugerea unei caramizi va genera power up
	powerup = std::rand() % 10000;

	// pozitionare elemente in scena
	// am folosit functiile de translatie, scalare si rotire din laborator

	modelMatrix = glm::mat3(1);
	modelMatrix *= Transformari2D::Translate(0.0f, 695.0f);
	RenderMesh2D(meshes["wallUp"], shaders["VertexColor"], modelMatrix);
	wall_up->updateCoordinates(25.0f, 695.0f);

	modelMatrix = glm::mat3(1);
	modelMatrix *= Transformari2D::Translate(0.0f, 45.0f);
	RenderMesh2D(meshes["wallLeft"], shaders["VertexColor"], modelMatrix);
	wall_left->updateCoordinates(25.0f, 45.0f);

	modelMatrix = glm::mat3(1);
	modelMatrix *= Transformari2D::Translate(1255.0f, 45.0f);
	RenderMesh2D(meshes["wallRight"], shaders["VertexColor"], modelMatrix);
	wall_right->updateCoordinates(1255.0f, 45.0f);
	
	// in variabila mouseXPos este retinuta pozitia mouse-ului care este daca ca argument functiei de translatie pentru platforma
	modelMatrix = glm::mat3(1);
	modelMatrix *= Transformari2D::Translate(mouseXPos, 10.0f);
	RenderMesh2D(meshes["platform"], shaders["VertexColor"], modelMatrix);
	platform->updateCoordinates(mouseXPos, 10.0f);

	// reprezentarea caramizilor ce nu au fost distruse inca
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 10; j++) {
			if (meshes["brick" + std::to_string(i) + std::to_string(j)] != NULL && scaleBrick[i * 10 + j] == false) {
				modelMatrixBrick = glm::mat3(1);
				modelMatrixBrick *= Transformari2D::Translate(j * 110.0f + 95.0f, i * 90.0f + 200.0f);
				RenderMesh2D(meshes["brick" + std::to_string(i) + std::to_string(j)], shaders["VertexColor"], modelMatrixBrick);
			}
		}
	}

	modelMatrix = glm::mat3(1);

	// 2 cazuri
	// unul in care mingea nu a fost lansata -> mingea e lansata la apararea click stanga
	// mingea "sta" deasupra platformei pana cand este lansata
	if (launchBall == false) {
		modelMatrix *= Transformari2D::Translate(mouseXPos + platformLen / 2, 50.0f);
		circle->updateCoordinates(mouseXPos + platformLen / 2, 50.0f);
	}
	else {
		// daca suntem la prima aruncare, singura posibilitate este ca mingea sa mearga in sus
		if (firstAttempt == true) {
			if (circle->getYCoord() + radius >= wall_up->getYCoord()) {
				ty = -ty;
			}

			if (circle->getYCoord() <= platform->getYCoord() + platformWid + radius + 2.0f) {
				firstAttempt = false;
			}

			// daca loveste caramida
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 10; j++) {
					modelMatrixBrick = glm::mat3(1);

					// daca loveste caramida (i,j) in una dintre cele 4 laturi, se schimba directia prin reflexie
					// 2 functii care verifica coliziunea
					if (hitYBrick(i, j) == true) {
						ty = -ty;
					}
					if (hitXBrick(i, j) == true) {
						tx = -tx;
					}

					// caz in care caramida trebuie scalata si nu a disparut de tot
					// animatie disparitie caramida
					if (scaleBrick[i * 10 + j] == true  && meshes["brick" + std::to_string(i) + std::to_string(j)] != NULL) {

						// micsorez factorul de scalare al caramizii (i,j) pana cand factorul devine 0
						if (sx[i * 10 + j] > 0 && sy[i * 10 + j] > 0) {
							sx[i * 10 + j] -= deltaTimeSeconds * 2.0f;
							sy[i * 10 + j] -= deltaTimeSeconds * 2.0f;
						}
						// atunci caramida este initializata cu NULL pentru a nu mai fi pusa in scena
						else {
							sy[i * 10 + j] = 0;
							sx[i * 10 + j] = 0;
							meshes["brick" + std::to_string(i) + std::to_string(j)] = NULL;
						}

						// translatie cu scalare
						modelMatrixBrick *= Transformari2D::Translate(j * 110.0f + 95.0f, i * 90.0f + 200.0f);
						modelMatrixBrick *= Transformari2D::Translate(brickLen / 2, brickWid / 2);
						modelMatrixBrick *= Transformari2D::Scale(sx[i * 10 + j], sy[i * 10 + j]);
						modelMatrixBrick *= Transformari2D::Translate(-brickLen / 2, -brickWid / 2);
					}
					else {
						// translatie normala
						modelMatrixBrick *= Transformari2D::Translate(j * 110.0f + 95.0f, i * 90.0f + 200.0f);
					}

					RenderMesh2D(meshes["brick" + std::to_string(i) + std::to_string(j)], shaders["VertexColor"], modelMatrixBrick);
				}
			}

			// in acest caz, daca s a lovit caramida, posibil sa se modifice oricare coordonata
			if (hitCaramida == true) {
				translateY += ty * deltaTimeSeconds * 500;
				translateX += tx * deltaTimeSeconds * 500;
			}
			// altfel doar y
			else {
				translateY += ty * deltaTimeSeconds * 500;
			}
		}
		// mingea este pusa in scena dupa cel putin o coliziune cu peretele superior
		else {
			// verificare coliziune perete superior
			if (circle->getYCoord() + radius >= wall_up->getYCoord()) {
				ty = -ty;
			}
			// perete dreapta
			if (circle->getXCoord() + radius >= wall_right->getXCoord()) {
				tx = -tx;
			}
			// perete stanga
			if (circle->getXCoord() + radius <= wall_left->getXCoord() + 25.0f) {
				tx = -tx;
			}
			// verificare coliziune platforma
			if (circle->getXCoord() >= platform->getXCoord() &&
				circle->getXCoord() <= platform->getXCoord() + platformLen &&
				circle->getYCoord() <= platform->getYCoord() + platformWid + radius) {

				// updatare coordonate si factori de translatie minge
				// reduc intervalul real la [-1,0] respectiv la [0,1] si calculez noii factori de translatie
				// conform enuntului
				if (circle->getXCoord() < platform->getXCoord() + platformLen / 2) {
					newX = (circle->getXCoord() - platform->getXCoord()) / (platformLen / 2) - 1;
					tx = newX;
					ty = sin(acos(newX));
				}
				if (circle->getXCoord() >= platform->getXCoord() + platformLen / 2) {
					newX = (circle->getXCoord() - (platform->getXCoord() + platformLen / 2)) / (platformLen / 2);
					tx = newX;
					ty = sin(acos(newX));
				}

				// variabila ce ma anunta ca am lovit platforma
				hitPlatform = true;
			}

			// verificare coliziune cu caramizi
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 10; j++) {
					modelMatrixBrick = glm::mat3(1);
					if (hitYBrick(i, j) == true) {
						ty = -ty;
					}
					if (hitXBrick(i, j) == true) {
						tx = -tx;
					}

					if (scaleBrick[i * 10 + j] == true && meshes["brick" + std::to_string(i) + std::to_string(j)] != NULL) {

						if (sx[i * 10 + j] > 0 && sy[i * 10 + j] > 0) {
							sx[i * 10 + j] -= deltaTimeSeconds * 2.0f;
							sy[i * 10 + j] -= deltaTimeSeconds * 2.0f;
						}
						else {
							sy[i * 10 + j] = 0;
							sx[i * 10 + j] = 0;
							meshes["brick" + std::to_string(i) + std::to_string(j)] = NULL;
						}

						modelMatrixBrick *= Transformari2D::Translate(j * 110.0f + 95.0f, i * 90.0f + 200.0f);
						modelMatrixBrick *= Transformari2D::Translate(brickLen / 2, brickWid / 2);
						modelMatrixBrick *= Transformari2D::Scale(sx[i * 10 + j], sy[i * 10 + j]);
						modelMatrixBrick *= Transformari2D::Translate(-brickLen / 2, -brickWid / 2);
					}
					else {
						modelMatrixBrick *= Transformari2D::Translate(j * 110.0f + 95.0f, i * 90.0f + 200.0f);
					}

					RenderMesh2D(meshes["brick" + std::to_string(i) + std::to_string(j)], shaders["VertexColor"], modelMatrixBrick);

					// verificare daca s-a generat un numar care sa genereze power-up pentru o caramida distrusa
					if (powerup == i * 10 + j) {
						rotatePowerUp[i * 10 + j] = true;
					}
				}
			}

			translateY += ty * deltaTimeSeconds * 500;
			translateX += tx * deltaTimeSeconds * 500;

			// verificare daca mingea a trecut de platforma si nu a lovit-o
			// in aceasta situatie se updateaza numarul de vieti retinut in num, de reinitializeaza factorii de translatie ai 
			// mingii initiali si se asteapta ca mingea sa fie trimisa iar in scena prin actiune click stanga
			if (circle->getYCoord() + radius < platform->getYCoord() + platformWid && hitPlatform == false) {
				firstAttempt = true;
				launchBall = false;
				tx = 1.0f;
				ty = 1.0f;
				if (num == 1) {
					num = 3;
				}
				else {
					num--;
				}
			}

			if (hitPlatform == true) {
				hitPlatform = false;
			}
		}

		modelMatrix *= Transformari2D::Translate(translateX, translateY);
		circle->updateCoordinates(translateX, translateY);
	}

	RenderMesh2D(meshes["ball"], shaders["VertexColor"], modelMatrix);

	// punere in scena vieti, avem num vieti, unde maxim este 3 si minim este 1
	for (int i = 0; i < num; i++) {
		modelMatrix = glm::mat3(1);
		modelMatrix *= Transformari2D::Translate(i * 30.0f + 20.0f, 15.0f);
		RenderMesh2D(meshes["life" + std::to_string(i)], shaders["VertexColor"], modelMatrix);
	}

	// punere in scena power-ups
	// pentru fiecare caramida distrusa se va genera un patrat power-up
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 10; j++) {
			if (rotatePowerUp[i * 10 + j] == true && scaleBrick[i * 10 + j] == true) {
				
				modelMatrixPowerup = glm::mat3(1);
				rx[i * 10 + j] -= deltaTimeSeconds * 100;
				translatePU[i * 10 + j] -= deltaTimeSeconds * 300;

				modelMatrixPowerup *= Transformari2D::Translate(j * 110 + 95 + brickLen / 2, translatePU[i * 10 + j]);
				modelMatrixPowerup *= Transformari2D::Translate(square / 2, square / 2);
				modelMatrixPowerup *= Transformari2D::Rotate(rx[i * 10 + j]);
				modelMatrixPowerup *= Transformari2D::Translate(-square / 2, -square / 2);
				RenderMesh2D(meshes["square" + std::to_string(i) + std::to_string(j)], shaders["VertexColor"], modelMatrixPowerup);
			}
		}
	}
}

void Tema1::FrameEnd() {

}

void Tema1::OnInputUpdate(float deltaTime, int mods) {

}

void Tema1::OnMouseMove(int mouseX, int mouseY, int deltaX, int deltaY) {
	mouseXPos = (float)mouseX;
}

void Tema1::OnMouseBtnPress(int mouseX, int mouseY, int button, int mods) {
	if (IS_BIT_SET(button, GLFW_MOUSE_BUTTON_LEFT)) {
		launchBall = true;
		translateX = mouseXPos + platformLen / 2;
	}
}

// functie verificare coliziune in latime
bool Tema1::hitYBrick(int i, int j) {
	bool modify = false;

	if (scaleBrick[i * 10 + j] == false && meshes["brick" + std::to_string(i) + std::to_string(j)] != NULL &&
		(circle->getXCoord() >= j * 110.0f + 95.0f && circle->getXCoord() <= j * 110.0f + 95.0f + brickLen) &&
		(circle->getYCoord() + radius >= i * 90.0f + 200.0f && circle->getYCoord() + radius <= i * 90.0f + 200.0f + brickWid)) {
		scaleBrick[i * 10 + j] = true;
		modify = true;
	}
	if (scaleBrick[i * 10 + j] == false && meshes["brick" + std::to_string(i) + std::to_string(j)] != NULL &&
		(circle->getXCoord() >= j * 110.0f + 95.0f && circle->getXCoord() <= j * 110.0f + 95.0f + brickLen) &&
		(circle->getYCoord() - radius <= i * 90.0f + 200.0f + brickWid && circle->getYCoord() - radius >= i * 90.0f + 200.0f)) {
		scaleBrick[i * 10 + j] = true;
		modify = true;
	}

	return modify;
}

// functie verificare coliziune in lungime
bool Tema1::hitXBrick(int i, int j) {
	bool modify = false;

	if (scaleBrick[i * 10 + j] == false && meshes["brick" + std::to_string(i) + std::to_string(j)] != NULL &&
		(circle->getYCoord() >= i * 90.0f + 200.0f && circle->getYCoord() <= i * 90.0f + 200.0f + brickWid) &&
		(circle->getXCoord() + radius >= j * 110.0f + 95.0f && circle->getXCoord() + radius <= j * 110.0f + 95.0f + brickLen)) {
		modify = true;
		scaleBrick[i * 10 + j] = true;
	}
	if (scaleBrick[i * 10 + j] == false && meshes["brick" + std::to_string(i) + std::to_string(j)] != NULL &&
		(circle->getYCoord() >= i * 90.0f + 200.0f && circle->getYCoord() <= i * 90.0f + 200.0f + brickWid) &&
		(circle->getXCoord() - radius <= j * 110.0f + 95.0f + brickLen && circle->getXCoord() - radius >= j * 110.0f + 95.0f)) {
		modify = true;
		scaleBrick[i * 10 + j] = true;
	}

	return modify;
}

