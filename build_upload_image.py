import os

PREFIX = "codewisdom"
VERSION = "0.2.0"

base_path = os.getcwd()
build_paths = []


def main():
    if not mvn_build():
        print("mvn build failed")
    init_docker_build_paths()
    # docker_login()
    docker_build_and_push()


def mvn_build():
    mvn_status = os.system("mvn clean package -DskipTests")
    return mvn_status == 0


def init_docker_build_paths():
    list_paths = os.listdir(os.getcwd())
    for p in list_paths:
        if os.path.isdir(p):
            if(p.startswith("ts-")):
                build_path=base_path + "/" + p
                build_paths.append(build_path)


def docker_login():
    username = os.getenv("DOCKER_USERNAME")
    docker_hub_address = os.getenv("DOCKER_HUB_ADDRESS") or "registry.cn-hangzhou.aliyuncs.com"
    print(f"[DOCKER HUB LOGIN] login username:{username} address:{docker_hub_address}")
    print(f"[DOCKER HUB LOGIN] You should input your root password first and then dockerhub password")
    docker_login = os.system(f"sudo docker login --username={username} {docker_hub_address}")
    if not docker_login:
        print("docker login failed")


def docker_build_and_push():
    for build_path in build_paths:
        image_name = build_path.split("/")[-1]

        os.chdir(build_path)
        files = os.listdir(build_path)
        if "Dockerfile" in files:
            docker_build = os.system(f"sudo docker build . -t {PREFIX}/{image_name}:{VERSION}")
            if docker_build != 0:
                print("[FAIL]" + image_name + " build failed.")
            else:
                print("[SUCCESS]" + image_name + " build success.")

            docker_push = os.system(f"sudo docker push {PREFIX}/{image_name}:{VERSION}")
            if docker_push != 0:
                print("[FAIL]" + image_name + " push failed.")
            else:
                print("[SUCCESS]" + image_name + " push success.")


if __name__ == '__main__':
    main()

