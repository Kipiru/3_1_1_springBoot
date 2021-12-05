fetch('/admin/users')
    .then(response => response.json())
    .then(data => {
        buildTable(data);
    })
    .catch(console.error);

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

function deleteUser(id) {
    let url = '/admin/delete/' + id;
    fetch(url, {
        method: 'DELETE'
    }).then(r => r.json())
        .catch(console.error());
    const table = document.getElementById('usersTable');
    setTimeout(table.deleteRow(id).reload(), 500);
}

function updateData() {
    location.reload();
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
            $(".modal-body #Roles").val(user.roles);

            $("#editButton").on('click', function () {
                updateUser();
                // window.location = "http://localhost:8080/admin/users";
            });
        })
        .catch(function (err) {
            console.log(err);
        });
}


async function updateUser() {

    let userId = document.getElementById("ID").value;
    let arr = [];

    // $("#Roles option").each(function()
    // {
    //     arr.push($(this).val());
    // });

    let user =
        {
            id: document.getElementById("ID").value,
            name: document.getElementById("Name").value,
            lastName: document.getElementById("Lastname").value,
            age: document.getElementById("Age").value,
            password: document.getElementById("Password").value,
            roles: $( "#Roles" ).val() || []
        }

    // roles: $('#Roles').val() || []
    // roles: $("#Roles #foo option:selected").val()
    const urlUPDATE = '/admin/update/' + userId;
    const putMethod = {
        method: 'PATCH',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(user)
    }
    fetch(urlUPDATE, putMethod)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(err => console.log(err));
    $("#editModalWindow .close").click();
}



//
// function createNewFunction() {
//     let nameForNew = document.getElementById("nameForNew").value;
//     let descripForNew = document.getElementById("descripForNew").value;
//     let priceForNew = document.getElementById("priceForNew").value;
//     let providersForNew = document.getElementById("providersForNew").value;
//     let good =
//         {
//             name: nameForNew,
//             description: descripForNew,
//             price: priceForNew,
//             providers: providersForNew
//         }
//
//     fetch('http://localhost:8080/api/v1/goods/', {
//         method: 'PUT',
//         headers: {
//             'Content-Type': 'application/json;charset=utf-8'
//         },
//         body: JSON.stringify(good)
//     });
// };