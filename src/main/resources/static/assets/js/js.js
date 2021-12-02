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
        for(let j = 0; j < data[i].roles.length; j++) {
            let getRoles = data[i].roles[j].role + " ";
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
                                   value="edit" onclick="showEditModal(${data[i].id})"/>
                            <input type="button" class="button btn-danger"
                                  value="delete" onclick="deleteUser(${data[i].id})"/>
                        </td>
                    </tr>`
        table.innerHTML += row;
    }
}

function deleteUser(id){
    let url = '/admin/delete/' + id;
    fetch(url, {
        method: 'DELETE'
    }).then(r => r.json())
        .catch(console.error());
    const table = document.getElementById('usersTable');
    setTimeout(table.deleteRow(id).reload(), 500);
}

function updateData()
{
   location.reload();
}

function showEditModal(id) {
    let url = '/admin/edit/' + id;
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            console.log(data);
            $("#editModal").modal();
            $(".modal-body #id-for-good-to-edit").val(id);
            $(".modal-body #name-old").val(data.goodsName);
            $(".modal-body #description-old").val(data.goodsDescription);
            $(".modal-body #price-old").val(data.goodsPrice);
            $(document).on("click", ".toUpdate", function () {
                editGoodFunction(id);
                window.location = "http://localhost:8080/";
            });
        })
        .catch(function (err) {
            console.log(err);
        });
}
//
//
// function editGoodFunction(id) {
//
//     let idgood = id;
//     let namegood = document.getElementById("name-old").value;
//     let descriptiongood = document.getElementById("description-old").value;
//     let pricegood = document.getElementById("price-old").value;
//
//     let good =
//         {
//             goodsID: idgood,
//             goodsName: namegood,
//             goodsDescription: descriptiongood,
//             goodsPrice: pricegood,
//             providerSet: null
//         }
//
//     const urlUPDATE = 'http://localhost:8080/api/v1/goods/' + id;
//     const putMethod = {
//         method: 'PUT',
//         headers: {
//             'Content-type': 'application/json; charset=UTF-8' // Indicates the content
//         },
//         body: JSON.stringify(good) // We send data in JSON format
//     }
//     fetch(urlUPDATE, putMethod)
//         .then(response => response.json())
//         .then(data => console.log(data)) // Manipulate the data retrieved back, if we want to do something with it
//         .catch(err => console.log(err))
// };
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