#include "Tema2.h"

#include <vector>
#include <string>
#include <iostream>

#include <Core/Engine.h>

using namespace std;

Tema2::Tema2() {
}

Tema2::~Tema2() {
}

void Tema2::Init() {
	setWhiteBall = true;
	hitBall = false;
	angularStepOY = 0.0f;
	coeficient = 0.0f;

	camera = new Lab::Camera();
	camera->Set(glm::vec3(0, 4, 0), glm::vec3(0, 1, 0), glm::vec3(0, 0, -1));
	projectionMatrix = glm::perspective(RADIANS(60), window->props.aspectRatio, 0.01f, 200.0f);

	// initializarea scenei
	initialize();

	// shader folosit pentru tac
	{
		Shader *shader = new Shader("ShaderTema2");
		shader->AddShader("Source/Laboratoare/Tema2/Shaders/VertexShader.glsl", GL_VERTEX_SHADER);
		shader->AddShader("Source/Laboratoare/Tema2/Shaders/FragmentShader.glsl", GL_FRAGMENT_SHADER);
		shader->CreateAndLink();
		shaders[shader->GetName()] = shader;
	}
}

void Tema2::FrameStart() {
	glClearColor(0, 0, 0, 1);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glm::ivec2 resolution = window->GetResolution();

	glViewport(0, 0, resolution.x, resolution.y);
}

void Tema2::Update(float timeDeltaSeconds) {
	//masa
	{
		glm::mat4 modelMatrix = glm::mat4(1);
		modelMatrix *= Transform3D::Translate(table->getX(), table->getY(), table->getZ());
		modelMatrix *= Transform3D::Scale(table->getWidth(), table->getHeight(), table->getLenght());
		RenderMesh3D(table->getMesh(), shaders["Color"], modelMatrix, table->getColor());
	}

	//bila alba
	{
		glm::mat4 modelMatrix = glm::mat4(1);
		modelMatrix *= Transform3D::Translate(whiteBall->getX(), whiteBall->getY(), whiteBall->getZ());
		modelMatrix *= Transform3D::Scale(2 * whiteBall->getRadius(), 2 * whiteBall->getRadius(), 2 * whiteBall->getRadius());
		RenderMesh3D(whiteBall->getMesh(), shaders["Color"], modelMatrix, whiteBall->getColor());
	}

	//bila neagra
	{
		glm::mat4 modelMatrix = glm::mat4(1);
		modelMatrix *= Transform3D::Translate(blackBall->getX(), blackBall->getY(), blackBall->getZ());
		modelMatrix *= Transform3D::Scale(2 * blackBall->getRadius(), 2 * blackBall->getRadius(), 2 * blackBall->getRadius());
		RenderMesh3D(blackBall->getMesh(), shaders["Color"], modelMatrix, blackBall->getColor());
	}

	// manta
	{
		glm::mat4 modelMatrix = glm::mat4(1);
		modelMatrix *= Transform3D::Translate(mantaBack->getX(), mantaBack->getY(), mantaBack->getZ());
		modelMatrix *= Transform3D::Scale(mantaBack->getWidth(), mantaBack->getHeight(), mantaBack->getLenght());
		RenderMesh3D(mantaBack->getMesh(), shaders["Color"], modelMatrix, mantaBack->getColor());
	}
	{
		glm::mat4 modelMatrix = glm::mat4(1);
		modelMatrix *= Transform3D::Translate(mantaFront->getX(), mantaFront->getY(), mantaFront->getZ());
		modelMatrix *= Transform3D::Scale(mantaFront->getWidth(), mantaFront->getHeight(), mantaFront->getLenght());
		RenderMesh3D(mantaFront->getMesh(), shaders["Color"], modelMatrix, mantaFront->getColor());
	}
	{
		glm::mat4 modelMatrix = glm::mat4(1);
		modelMatrix *= Transform3D::Translate(mantaLeft->getX(), mantaLeft->getY(), mantaLeft->getZ());
		modelMatrix *= Transform3D::Scale(mantaLeft->getWidth(), mantaLeft->getHeight(), mantaLeft->getLenght());
		RenderMesh3D(mantaLeft->getMesh(), shaders["Color"], modelMatrix, mantaLeft->getColor());
	}
	{
		glm::mat4 modelMatrix = glm::mat4(1);
		modelMatrix *= Transform3D::Translate(mantaRight->getX(), mantaRight->getY(), mantaRight->getZ());
		modelMatrix *= Transform3D::Scale(mantaRight->getWidth(), mantaRight->getHeight(), mantaRight->getLenght());
		RenderMesh3D(mantaRight->getMesh(), shaders["Color"], modelMatrix, mantaRight->getColor());
	}

	// buzunare
	{
		for (int i = 0; i < 6; i++) {
			glm::mat4 modelMatrix = glm::mat4(1);
			modelMatrix *= Transform3D::Translate(pockets[i]->getX(), pockets[i]->getY(), pockets[i]->getZ());
			modelMatrix *= Transform3D::Scale(2 * pockets[i]->getRadius(), 2 * pockets[i]->getRadius(), 2 * pockets[i]->getRadius());
			RenderMesh3D(pockets[i]->getMesh(), shaders["Color"], modelMatrix, pockets[i]->getColor());
		}
	}

	// bile rosii
	{
		for (int i = 0; i < 7; i++) {
			glm::mat4 modelMatrix = glm::mat4(1);
			modelMatrix *= Transform3D::Translate(redBalls[i]->getX(), redBalls[i]->getY(), redBalls[i]->getZ());
			modelMatrix *= Transform3D::Scale(2 * redBalls[i]->getRadius(), 2 * redBalls[i]->getRadius(), 2 * redBalls[i]->getRadius());
			RenderMesh3D(redBalls[i]->getMesh(), shaders["Color"], modelMatrix, redBalls[i]->getColor());
		}
	}

	// bile galbene
	{
		for (int i = 0; i < 7; i++) {
			glm::mat4 modelMatrix = glm::mat4(1);
			modelMatrix *= Transform3D::Translate(yellowBalls[i]->getX(), yellowBalls[i]->getY(), yellowBalls[i]->getZ());
			modelMatrix *= Transform3D::Scale(2 * yellowBalls[i]->getRadius(), 2 * yellowBalls[i]->getRadius(), 2 * yellowBalls[i]->getRadius());
			RenderMesh3D(yellowBalls[i]->getMesh(), shaders["Color"], modelMatrix, yellowBalls[i]->getColor());
		}
	}

	//tac
	{
		if (setWhiteBall == false && hitBall == true) {
			cout << tac->getX() << " " << tac->getY() << " " << tac->getZ() << endl;
			glm::mat4 modelMatrix = glm::mat4(1);
			modelMatrix *= Transform3D::Translate(tac->getX(), tac->getY(), tac->getZ());
			modelMatrix *= Transform3D::Translate(-tac->getX(), -tac->getY(), -(tac->getLenght() / 2.0f + whiteBall->getRadius()));
			modelMatrix *= Transform3D::RotateOY(angularStepOY);
			modelMatrix *= Transform3D::Translate(tac->getX(), tac->getY(), tac->getLenght() / 2.0f + whiteBall->getRadius());
			modelMatrix *= Transform3D::RotateOX(-0.2f);
			modelMatrix *= Transform3D::Scale(tac->getWidth(), tac->getHeight(), tac->getLenght());
			RenderSimpleMesh(tac->getMesh(), shaders["ShaderTema2"], modelMatrix, tac->getColor());
		}
		else {
			glm::mat4 modelMatrix = glm::mat4(1);
			modelMatrix *= Transform3D::Translate(tac->getX(), tac->getY(), tac->getZ());
			modelMatrix *= Transform3D::Scale(tac->getWidth(), tac->getHeight(), tac->getLenght());
			RenderMesh3D(tac->getMesh(), shaders["Color"], modelMatrix, tac->getColor());
		}
	}
}

void Tema2::FrameEnd() {
	DrawCoordinatSystem(camera->GetViewMatrix(), projectionMatrix);
}

// functia de mesh pentru tac
void Tema2::RenderSimpleMesh(Mesh *mesh, Shader *shader, const glm::mat4 & modelMatrix, const glm::vec3 &color) {
	if (!mesh || !shader || !shader->GetProgramID())
		return;

	glUseProgram(shader->program);

	int location = glGetUniformLocation(shader->program, "Model");
	glUniformMatrix4fv(location, 1, GL_FALSE, glm::value_ptr(modelMatrix));

	location = glGetUniformLocation(shader->program, "View");
	glm::mat4 viewMatrix = camera->GetViewMatrix();
	glUniformMatrix4fv(location, 1, GL_FALSE, glm::value_ptr(viewMatrix));

	location = glGetUniformLocation(shader->program, "Projection");
	glUniformMatrix4fv(location, 1, GL_FALSE, glm::value_ptr(projectionMatrix));

	location = glGetUniformLocation(shader->program, "color");
	glUniform3fv(location, 1, glm::value_ptr(color));

	// trimiterea la vertex xhader a coeficientului care deplaseaza tacul fata-spate pe directia z
	location = glGetUniformLocation(shader->program, "coef");
	glUniform1f(location, coeficient);

	glBindVertexArray(mesh->GetBuffers()->VAO);
	glDrawElements(mesh->GetDrawMode(), static_cast<int>(mesh->indices.size()), GL_UNSIGNED_SHORT, 0);
}

// functie de mesh folosita la desenarea celorlalte obiecte
void Tema2::RenderMesh3D(Mesh * mesh, Shader * shader, const glm::mat4 & modelMatrix, const glm::vec3 & color) {
	if (!mesh || !shader || !shader->program)
		return;

	shader->Use();
	glUniformMatrix4fv(shader->loc_view_matrix, 1, GL_FALSE, glm::value_ptr(camera->GetViewMatrix()));
	glUniformMatrix4fv(shader->loc_projection_matrix, 1, GL_FALSE, glm::value_ptr(projectionMatrix));
	glUniformMatrix4fv(shader->loc_model_matrix, 1, GL_FALSE, glm::value_ptr(modelMatrix));
	glUniform3f(shader->GetUniformLocation("color"), color.r, color.g, color.b);

	mesh->Render();
}

void Tema2::OnInputUpdate(float deltaTime, int mods) {
	// deplasare bila alba pe masa pentru a face lovitura
	float speed = 0.8f;

	if (setWhiteBall == true) {
		if (window->KeyHold(GLFW_KEY_W) && whiteBall->getZ() - whiteBall->getRadius() > (table->getLenght() / 3.0f) / 2.0f) {
			whiteBall->updateZ(-deltaTime * speed);
		}
		if (window->KeyHold(GLFW_KEY_S) && whiteBall->getZ() + whiteBall->getRadius() < table->getLenght() / 2.0f - 0.1f) {
			whiteBall->updateZ(deltaTime * speed);
		}
		if (window->KeyHold(GLFW_KEY_A) && whiteBall->getX() - whiteBall->getRadius() > -(table->getWidth() / 2.0f - 0.1f)) {
			whiteBall->updateX(-deltaTime * speed);
		}
		if (window->KeyHold(GLFW_KEY_D) && whiteBall->getX() + whiteBall->getRadius() < table->getWidth() / 2.0f - 0.1f) {
			whiteBall->updateX(deltaTime * speed);
		}

		// cand se apasa SPACE se trece in Third View Person
		if (window->KeyHold(GLFW_KEY_SPACE)) {
			setWhiteBall = false;
			hitBall = true;
			angularStepOY = 0;
			camera->Set(glm::vec3(whiteBall->getX(), whiteBall->getY() + 0.4f, whiteBall->getZ() + 0.9f), glm::vec3(whiteBall->getX(), whiteBall->getY(), whiteBall->getZ()), glm::vec3(0, 1, 0));
			// update la distanta fata de target - bila alba
			float x = std::pow(whiteBall->getX() - whiteBall->getX(), 2);
			float y = std::pow(whiteBall->getY() - (whiteBall->getY() + 0.4f), 2);
			float z = std::pow(whiteBall->getZ() - (whiteBall->getZ() + 0.9f), 2);
			float d = glm::sqrt(x + y + z);
			camera->setNewDistanceToTarget(d);
		}
	}

	// plasarea tacului fata de bila alba
	tac->setX(whiteBall->getX());
	tac->setZ(whiteBall->getZ() + tac->getLenght() / 2.0f + whiteBall->getRadius());

	// se calculeaza coeficinetul de deplasare al tacului fata-spate
	if ((hitBall == true) && (window->MouseHold(GLFW_MOUSE_BUTTON_LEFT))) {
		coeficient = std::abs(std::cos(Engine::GetElapsedTime())) / 3.0f;
	}
}

void Tema2::OnMouseMove(int mouseX, int mouseY, int deltaX, int deltaY) {
	// la miscarea mouse-ului in mod Third Person, camera se deplaseaza si odata cu ea si tacul
	float sensivity = 0.001f;;
	if (hitBall == true && window->MouseHold(GLFW_MOUSE_BUTTON_RIGHT)) {
		camera->RotateThirdPerson_OY(deltaX * sensivity);
		angularStepOY += deltaX * sensivity;
	}
}

void Tema2::OnMouseBtnRelease(int mouseX, int mouseY, int button, int mods) {
	// cand se elibereaza butonul Mouse Stanga se trece in mod view top-down si se revine la setarile initiale
	if (IS_BIT_SET(button, GLFW_MOUSE_BUTTON_LEFT)) {
		coeficient = 0.0f;
		setWhiteBall = true;
		hitBall = false;
		camera->Set(glm::vec3(0, 4, 0), glm::vec3(0, 1, 0), glm::vec3(0, 0, -1));
	}
}

void Tema2::initialize() {
	redBalls.clear();
	yellowBalls.clear();
	pockets.clear();

	// initializare masa
	tableM = new Mesh("table");
	tableM->LoadMesh(RESOURCE_PATH::MODELS + "Primitives", "box.obj");
	table = new Manta(0, 1, 0, 1.7f, 0.5f, 2.4f, tableM, glm::vec3(0, 1, 0));

	// initializare mante
	float mantaH = 1 + (table->getHeight() + 0.1f) / 2.0f;
	mantaFront = new Manta(0, mantaH, (table->getLenght() - 0.1f) / 2.0f, table->getWidth(), 0.1, 0.1, tableM, glm::vec3(0, 0.5f, 0));
	mantaBack = new Manta(0, mantaH, -((table->getLenght() - 0.1f) / 2.0f), table->getWidth(), 0.1, 0.1, tableM, glm::vec3(0, 0.5f, 0));
	mantaRight = new Manta(-((table->getWidth() - 0.1f) / 2.0f), mantaH, 0, 0.1f, 0.1f, table->getLenght() - 0.2f, tableM, glm::vec3(0, 0.5f, 0));
	mantaLeft = new Manta((table->getWidth() - 0.1f) / 2.0f, mantaH, 0, 0.1f, 0.1f, table->getLenght() - 0.2f, tableM, glm::vec3(0, 0.5f, 0));
	
	// initializare bila alba si bila neagra
	ballM = new Mesh("ball");
	ballM->LoadMesh(RESOURCE_PATH::MODELS + "Primitives", "sphere.obj");
	float ballH = 1 + (table->getHeight() + 0.08f) / 2.0f;
	whiteBall = new Ball(0, ballH, (table->getLenght() - 0.2f) / 3.0f, 0.04f, ballM, glm::vec3(1, 1, 1));
	blackBall = new Ball(0, ballH, 0, 0.04f, ballM, glm::vec3(0, 0, 0));

	// initializarea bilelor rosii si galbele
	{
		for (int i = 0; i < 7; i++) {
			redBalls.push_back(new Ball(0, ballH, 0, 0.04f, ballM, glm::vec3(1, 0, 0)));
			yellowBalls.push_back(new Ball(0, ballH, 0, 0.04f, ballM, glm::vec3(1, 1, 0)));
		}

		float radius = 0.04f;
		float diameter = 0.08f;
		float distance = 0.005f;
		float third = table->getLenght() / 3.0f;

		float line1 = -third / 2.0f;
		redBalls[0]->setZ(line1);

		float line2 = line1 - diameter;
		redBalls[1]->setX(radius + distance);
		redBalls[1]->setZ(line2);
		yellowBalls[0]->setX(-(radius + distance));
		yellowBalls[0]->setZ(line2);

		float line3 = line2 - diameter;
		redBalls[2]->setX(-(diameter + distance));
		redBalls[2]->setZ(line3);
		blackBall->setZ(line3);
		yellowBalls[1]->setX(diameter + distance);
		yellowBalls[1]->setZ(line3);

		float line4 = line3 - diameter;
		redBalls[3]->setX(-(radius + distance));
		redBalls[3]->setZ(line4);
		redBalls[4]->setX(radius + distance);
		redBalls[4]->setZ(line4);
		yellowBalls[2]->setX(-(redBalls[4]->getX() + redBalls[4]->getX() + radius));
		yellowBalls[2]->setZ(line4);
		yellowBalls[3]->setX(redBalls[4]->getX() + redBalls[4]->getX() + radius);
		yellowBalls[3]->setZ(line4);

		float line5 = line4 - diameter;
		yellowBalls[4]->setX(-(diameter + distance));
		yellowBalls[4]->setZ(line5);
		yellowBalls[6]->setX(diameter + distance);
		yellowBalls[6]->setZ(line5);
		redBalls[5]->setX(-(yellowBalls[6]->getX() + yellowBalls[6]->getX()));
		redBalls[5]->setZ(line5);
		yellowBalls[5]->setZ(line5);
		redBalls[6]->setX(yellowBalls[6]->getX() + yellowBalls[6]->getX());
		redBalls[6]->setZ(line5);
	}

	// initializare buzunare
	{
		float x = table->getWidth() / 2.0f - 0.1f;
		float z = table->getLenght() / 2.0f - 0.1f;
		float pocketH = 1 + table->getHeight() / 2.0f;
		for (int i = 0; i < 6; i++) {
			pockets.push_back(new Ball(0, pocketH, 0, 0.075f, ballM, glm::vec3(0, 0, 0)));
		}
		pockets[0]->setX(-x);
		pockets[0]->setZ(z);
		pockets[1]->setX(x);
		pockets[1]->setZ(z);
		pockets[2]->setX(-x);
		pockets[3]->setX(x);
		pockets[4]->setX(-x);
		pockets[4]->setZ(-z);
		pockets[5]->setX(x);
		pockets[5]->setZ(-z);
	}

	// initializare tac
	float tacH = 1.0f + table->getHeight() / 2.0f + 3 * mantaFront->getHeight() / 2.0f;
	tac = new Tac(0, tacH, 0, 0.02f, 0.02f, 1.0f, tableM, glm::vec3(0.36f, 0.2f, 0.09f));
}
