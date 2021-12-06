fetch('/auth')
    .then(response => response.json())
    .then(data => {
        userAuthority(data);
    })
    .catch(console.error);

fetch('/admin/users')
    .then(response => response.json())
    .then(data => {
        buildTable(data);
    })
    .catch(console.error);

fetch('/admin/roles')
    .then(response => response.json())
    .then(data => {
        showRoles(data);
    })
    .catch(console.error);

function userAuthority(data) {
    document.getElementById("currentUser").innerHTML = `<strong><p>${data.name}</p></strong>`;
    const currentRoles = document.getElementById("currentRoles");
    for (let i = 0; i < data.roles.length; i++) {
        let row =
            `<strong><p>${data.roles[i].roleName}</p></strong>`;
        currentRoles.innerHTML += row;
    }
    const table = document.getElementById("currentUserTable");
    let userRoles = "";
    for (let j = 0; j < data.roles.length; j++) {
        let getRoles = data.roles[j].roleName + " ";
        userRoles += getRoles;
    }
    table.innerHTML =
        `<tr>
                        <td>${data.id}</td>
                        <td>${data.name}</td>
                        <td>${data.lastName}</td>
                        <td>${data.age}</td>
                        <td>${data.password}</td>
                        <td>${userRoles}</td>
                    </tr>`


}

function buildTable(data) {
    const table = document.getElementById('usersTable');
    for (let i = 0; i < data.length; i++) {
        let userRoles = "";
        for (let j = 0; j < data[i].roles.length; j++) {
            let getRoles = data[i].roles[j].roleName + " ";
            userRoles += getRoles;
        }
        let row =
            `<tr>
                        <td>${data[i].id}</td>
                        <td>${data[i].name}</td>
                        <td>${data[i].lastName}</td>
                        <td>${data[i].age}</td>
                        <td>${data[i].password}</td>
                        <td>${userRoles}</td>
                        <td>
                            <input type="button" class="button btn-info" id="show-edit-modal"
                                   value="edit" onclick="showEditModalWindow(${data[i].id})"/>
                            <input type="button" class="button btn-danger"
                                  value="delete" onclick="deleteUser(${data[i].id})"/>
                        </td>
                    </tr>`
        table.innerHTML += row;
    }
}

function showRoles(data) {
    const updateSelect = document.getElementById("Roles");
    const newSelect = document.getElementById("newRoles");
    for (let i = 0; i < data.length; i++) {
        let row =
            `<option>${data[i].roleName}</option>`;
        updateSelect.innerHTML += row;
        newSelect.innerHTML += row;
    }
}

function deleteUser(id) {
    let url = '/admin/delete/' + id;
    fetch(url, {
        method: 'DELETE'
    }).then(r => r.json())
        .catch(console.error());
    const table = document.getElementById('usersTable');
    setTimeout(table.deleteRow(id).reload(), 500);
}


function showEditModalWindow(id) {
    let url = '/admin/user/' + id;
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (user) {
            console.log(user);
            $("#editModalWindow").modal();
            $(".modal-body #ID").val(user.id);
            $(".modal-body #Name").val(user.name);
            $(".modal-body #Lastname").val(user.lastName);
            $(".modal-body #Age").val(user.age);
            $(".modal-body #Password").val(user.password);

            $(document).on("click", ".toUpdate", function () {
                updateUser(id).then(function () {

                        // setTimeout(function(){
                        //     $( "#usersTable" ).load( "/admin #usersTable" );
                        // }, 500);
                        //
                        // setTimeout(function(){fetch('/admin/users')
                        //     .then(response => response.json())
                        //     .then(data => {
                        //         buildTable(data);
                        //     })
                        //     .catch(console.error);}, 1000);
                        location.reload();
                    }
                )
            });
        })
        .catch(function (err) {
            console.log(err);
        });
}

//                    NEW USER
document.getElementById("NewUserForm");

function addNewUser() {
    let id = document.getElementById('newID').value;
    let name = document.getElementById('newName').value;
    let lastName = document.getElementById('newLastname').value;
    let age = document.getElementById('newAge').value;
    let password = document.getElementById('newPassword').value;
    let roles = Array.from(document.querySelector("#newRoles")).filter(option => option.selected)
        .map(option => option.value);
    fetch("/admin/save", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            id: id,
            name: name,
            lastName: lastName,
            age: age,
            password: password,
            roles: roles
        })
    }).then(function () {
        window.location = "/admin";
    });
}


//                    EDIT USER
async function updateUser() {
    let user =
        {
            id: ID.value,
            name: Name.value,
            lastName: Lastname.value,
            age: Age.value,
            password: Password.value,
            roles: Array.from(document.querySelector("#Roles")).filter(option => option.selected)
                .map(option => option.value)
        }

    console.log(JSON.stringify(user))
    const urlUPDATE = '/admin/update/' + user.id;
    const putMethod = {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    }
    fetch(urlUPDATE, putMethod)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(err => console.log(err));
    $("#editModalWindow .close").click();
}




