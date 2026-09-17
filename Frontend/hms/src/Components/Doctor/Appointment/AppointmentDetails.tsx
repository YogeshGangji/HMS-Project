import { Anchor, Badge, Breadcrumbs, Card, Divider, Group, SimpleGrid, Stack, Tabs, Text } from '@mantine/core';
import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getAppointmentWithDetails } from '../../../Service/AppointmentService';
import { IconCalendar, IconClipboardHeart, IconMail, IconMessageCircle, IconNotes, IconPhone, IconPhoto, IconSettings, IconStethoscope, IconUser, IconVaccine } from '@tabler/icons-react';

const AppointmentDetails = () => {

    const items = [
        { title: 'Mantine', href: '#' },
        { title: 'Mantine hooks', href: '#' },
        { title: 'use-id', href: '#' },
    ].map((item, index) => (
        <Anchor href={item.href} key={index}>
            {item.title}
        </Anchor>
    ));

    const [appointment, setAppoiintment] = useState<any>({});

    useEffect(() => {
        getAppointmentWithDetails(id).then((data) => {
            console.log("Appointment Details: ", data);
            setAppoiintment(data);
        }).catch((err) => {
            console.error("Error fecthing appointment details : ", err);
        });
    }, [])

    const { id } = useParams();

    return (
        <div className="">
            <Breadcrumbs my={4}>

                <Link className='text-primary-400 hover:underline' to="/doctor/dashboard">Dashboard</Link>
                <Link className='text-primary-400 hover:underline' to="/doctor/appointments">Appointment</Link>
                <Text className='text-primary-400 '> Details </Text>
            </Breadcrumbs>
            <div className="">

                <Card shadow="sm" padding="lg" radius="md" withBorder>
                    <Group justify="space-between" mb="md">
                        <div>
                            <Text fw={600} size="lg">
                                {appointment.patientName}
                            </Text>

                            <Text size="sm" c="dimmed">
                                {new Date(appointment.appointmentTime).toLocaleString()}
                            </Text>
                        </div>

                        <Badge
                            color={appointment.status === "CANCELLED" ? "red" : "green"}
                            variant="light"
                        >
                            {appointment.status}
                        </Badge>
                    </Group>

                    <Divider mb="md" />

                    <SimpleGrid cols={2}>

                        <div>
                            <Text size="xs" c="dimmed">Doctor</Text>
                            <Text fw={500}>Dr. {appointment.doctorName}</Text>
                        </div>

                        <div>
                            <Text size="xs" c="dimmed">Phone</Text>
                            <Text>{appointment.patientPhone}</Text>
                        </div>

                        <div>
                            <Text size="xs" c="dimmed">Email</Text>
                            <Text>{appointment.patientEmail}</Text>
                        </div>

                        <div>
                            <Text size="xs" c="dimmed">Reason</Text>
                            <Text>{appointment.reason}</Text>
                        </div>

                        <div>
                            <Text size="xs" c="dimmed">Patient ID</Text>
                            <Text>{appointment.patientId}</Text>
                        </div>
                    </SimpleGrid>

                    <Divider my="md" />

                    <Text size="xs" c="dimmed">
                        Notes
                    </Text>

                    <Text size="sm">
                        {appointment.notes}
                    </Text>
                </Card>


                <Tabs variant='pills' defaultValue="medical" my="md" className='justify-center flex'>
                    <Tabs.List>
                        <Tabs.Tab value="medical" leftSection={<IconStethoscope />}>
                            Medical History
                        </Tabs.Tab>
                        <Tabs.Tab value="prescriptions" leftSection={<IconVaccine />}>
                            Prescriptions
                        </Tabs.Tab>
                        <Tabs.Tab value="reports" leftSection={<IconClipboardHeart />}>
                            Reports
                        </Tabs.Tab>
                    </Tabs.List>
                    <Divider my="md" />
                    <Tabs.Panel value="medical">
                        Medical
                    </Tabs.Panel>

                    <Tabs.Panel value="prescriptions">
                        prescriptions
                    </Tabs.Panel>

                    <Tabs.Panel value="reports">
                        reports
                    </Tabs.Panel>
                </Tabs>


            </div>

        </div >

    )
}

export default AppointmentDetails